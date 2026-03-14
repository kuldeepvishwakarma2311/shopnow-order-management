package com.shopnow.order.service.impl;

import com.shopnow.order.dto.OrderItemRequestDTO;
import com.shopnow.order.dto.OrderRequestDTO;
import com.shopnow.order.dto.OrderResponseDTO;
import com.shopnow.order.dto.PaymentDTO;
import com.shopnow.order.enums.OrderStatus;
import com.shopnow.order.enums.PaymentStatus;
import com.shopnow.order.enums.ShipmentStatus;
import com.shopnow.order.generic.BusinessException;
import com.shopnow.order.generic.ResourceNotFoundException;
import com.shopnow.order.mapper.OrderMapper;
import com.shopnow.order.model.Customer;
import com.shopnow.order.model.Inventory;
import com.shopnow.order.model.Order;
import com.shopnow.order.model.OrderItem;
import com.shopnow.order.model.Product;
import com.shopnow.order.model.Shipment;
import com.shopnow.order.model.Warehouse;
import com.shopnow.order.repository.CustomerRepository;
import com.shopnow.order.repository.InventoryRepository;
import com.shopnow.order.repository.OrderItemRepository;
import com.shopnow.order.repository.OrderRepository;
import com.shopnow.order.repository.ProductRepository;
import com.shopnow.order.repository.ShipmentRepository;
import com.shopnow.order.repository.WarehouseRepository;
import com.shopnow.order.service.OrderService;
import com.shopnow.order.service.PaymentService;
import com.shopnow.order.service.ShipmentService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Map<OrderStatus, EnumSet<OrderStatus>> ALLOWED_STATUS_TRANSITIONS = buildAllowedTransitions();

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentService paymentService;
    private final ShipmentService shipmentService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        if (request.getItems().stream().map(OrderItemRequestDTO::getProductId).anyMatch(productId -> productId == null)) {
            throw new BusinessException("Order contains invalid product reference");
        }

        PaymentDTO validatedPayment = paymentService.validatePayment(request.getPayment());
        if (validatedPayment.getPaymentStatus() == PaymentStatus.FAILED) {
            throw new BusinessException("Payment validation failed");
        }

        Warehouse warehouse = routeWarehouse(request.getItems());
        Order order = orderRepository.save(Order.builder()
            .customer(customer)
            .orderDate(LocalDateTime.now())
            .status(OrderStatus.PAYMENT_CONFIRMED)
            .paymentStatus(validatedPayment.getPaymentStatus())
            .totalAmount(BigDecimal.ZERO)
            .build());

        List<OrderItem> savedItems = createOrderItems(order, warehouse, request.getItems());
        BigDecimal totalAmount = savedItems.stream()
            .map(OrderItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.ALLOCATED_TO_WAREHOUSE);
        orderRepository.save(order);

        paymentService.recordPayment(order, validatedPayment, validatedPayment.getPaymentStatus());
        Shipment shipment = shipmentService.createShipment(order, warehouse);
        return orderMapper.toDto(order, savedItems, shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long id) {
        Order order = findOrder(id);
        return mapOrder(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrders() {
        return orderRepository.findAll().stream().map(this::mapOrder).toList();
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = findOrder(id);
        validateStatusTransition(order.getStatus(), status);
        applyInventoryEffectsForStatusChange(order, status);
        order.setStatus(status);
        syncShipmentForOrderStatus(order, status);
        return mapOrder(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order order = findOrder(id);
        if (EnumSet.of(OrderStatus.SHIPPED, OrderStatus.OUT_FOR_DELIVERY, OrderStatus.DELIVERED,
                OrderStatus.RETURNED, OrderStatus.REFUNDED).contains(order.getStatus())) {
            throw new BusinessException("Order cannot be cancelled after shipment processing has started");
        }
        releaseReservedInventory(order);
        order.setStatus(OrderStatus.CANCELLED);
        shipmentRepository.findByOrderId(order.getId()).ifPresent(shipment -> {
            shipment.setShipmentStatus(ShipmentStatus.CANCELLED);
            shipmentRepository.save(shipment);
        });
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream().map(this::mapOrder).toList();
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    private OrderResponseDTO mapOrder(Order order) {
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        Shipment shipment = shipmentRepository.findByOrderId(order.getId()).orElse(null);
        return orderMapper.toDto(order, items, shipment);
    }

    private Warehouse routeWarehouse(List<OrderItemRequestDTO> items) {
        Map<Long, Integer> requiredQty = aggregateRequiredQuantities(items);
        return warehouseRepository.findAll().stream()
            .filter(warehouse -> canFulfillWarehouse(warehouse.getId(), requiredQty))
            .max(Comparator.comparingInt(Warehouse::getCapacity))
            .orElseThrow(() -> new BusinessException("No warehouse can fulfill the full order"));
    }

    private boolean canFulfillWarehouse(Long warehouseId, Map<Long, Integer> requiredQty) {
        for (Map.Entry<Long, Integer> entry : requiredQty.entrySet()) {
            Inventory inventory = inventoryRepository.findByProductIdAndWarehouseId(entry.getKey(), warehouseId).orElse(null);
            if (inventory == null || inventory.getAvailableStock() < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    private List<OrderItem> createOrderItems(Order order, Warehouse warehouse, List<OrderItemRequestDTO> itemRequests) {
        Map<Long, Integer> aggregatedQuantities = aggregateRequiredQuantities(itemRequests);
        Map<Long, Product> products = productRepository.findAllById(
            aggregatedQuantities.keySet()
        ).stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        List<OrderItem> savedItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> itemRequest : aggregatedQuantities.entrySet()) {
            Product product = products.get(itemRequest.getKey());
            if (product == null) {
                throw new ResourceNotFoundException("Product not found: " + itemRequest.getKey());
            }
            Inventory inventory = inventoryRepository.findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                .orElseThrow(() -> new BusinessException("Inventory missing for product " + product.getSku()));
            if (inventory.getAvailableStock() < itemRequest.getValue()) {
                throw new BusinessException("Insufficient stock for product " + product.getSku());
            }
            inventory.setReservedStock(inventory.getReservedStock() + itemRequest.getValue());
            inventory.setAvailableStock(inventory.getStockQuantity() - inventory.getReservedStock());
            inventoryRepository.save(inventory);

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getValue()));
            OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(itemRequest.getValue())
                .price(product.getPrice())
                .subtotal(subtotal)
                .build();
            savedItems.add(orderItemRepository.save(orderItem));
        }
        return savedItems;
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus targetStatus) {
        if (currentStatus == targetStatus) {
            return;
        }
        EnumSet<OrderStatus> allowedStatuses = ALLOWED_STATUS_TRANSITIONS.getOrDefault(currentStatus, EnumSet.noneOf(OrderStatus.class));
        if (!allowedStatuses.contains(targetStatus)) {
            throw new BusinessException("Invalid order status transition from " + currentStatus + " to " + targetStatus);
        }
    }

    private void applyInventoryEffectsForStatusChange(Order order, OrderStatus newStatus) {
        if (newStatus == OrderStatus.SHIPPED && order.getStatus() != OrderStatus.SHIPPED) {
            finalizeReservedInventory(order);
        }
    }

    private void finalizeReservedInventory(Order order) {
        for (OrderItem item : orderItemRepository.findByOrderId(order.getId())) {
            Inventory inventory = inventoryRepository.findByProductIdAndWarehouseId(
                    item.getProduct().getId(),
                    shipmentRepository.findByOrderId(order.getId())
                        .orElseThrow(() -> new BusinessException("Shipment not found for order"))
                        .getWarehouse().getId())
                .orElseThrow(() -> new BusinessException("Inventory missing for shipped order item"));
            inventory.setStockQuantity(inventory.getStockQuantity() - item.getQuantity());
            inventory.setReservedStock(inventory.getReservedStock() - item.getQuantity());
            inventory.setAvailableStock(inventory.getStockQuantity() - inventory.getReservedStock());
            inventoryRepository.save(inventory);
        }
    }

    private void releaseReservedInventory(Order order) {
        shipmentRepository.findByOrderId(order.getId()).ifPresent(shipment -> {
            for (OrderItem item : orderItemRepository.findByOrderId(order.getId())) {
                Inventory inventory = inventoryRepository.findByProductIdAndWarehouseId(
                        item.getProduct().getId(),
                        shipment.getWarehouse().getId())
                    .orElseThrow(() -> new BusinessException("Inventory missing for cancelled order item"));
                inventory.setReservedStock(Math.max(0, inventory.getReservedStock() - item.getQuantity()));
                inventory.setAvailableStock(inventory.getStockQuantity() - inventory.getReservedStock());
                inventoryRepository.save(inventory);
            }
        });
    }

    private void syncShipmentForOrderStatus(Order order, OrderStatus status) {
        shipmentRepository.findByOrderId(order.getId()).ifPresent(shipment -> {
            ShipmentStatus shipmentStatus = mapOrderStatusToShipmentStatus(status);
            if (shipmentStatus != null) {
                shipment.setShipmentStatus(shipmentStatus);
                shipmentRepository.save(shipment);
            }
        });
    }

    private ShipmentStatus mapOrderStatusToShipmentStatus(OrderStatus status) {
        return switch (status) {
            case PACKED, ALLOCATED_TO_WAREHOUSE, PROCESSING -> ShipmentStatus.READY_FOR_PICKUP;
            case SHIPPED -> ShipmentStatus.SHIPPED;
            case OUT_FOR_DELIVERY -> ShipmentStatus.OUT_FOR_DELIVERY;
            case DELIVERED -> ShipmentStatus.DELIVERED;
            case CANCELLED -> ShipmentStatus.CANCELLED;
            default -> null;
        };
    }

    private Map<Long, Integer> aggregateRequiredQuantities(List<OrderItemRequestDTO> items) {
        Map<Long, Integer> requiredQty = new HashMap<>();
        for (OrderItemRequestDTO item : items) {
            requiredQty.merge(item.getProductId(), item.getQuantity(), Integer::sum);
        }
        return requiredQty;
    }

    private static Map<OrderStatus, EnumSet<OrderStatus>> buildAllowedTransitions() {
        Map<OrderStatus, EnumSet<OrderStatus>> transitions = new EnumMap<>(OrderStatus.class);
        transitions.put(OrderStatus.CREATED, EnumSet.of(OrderStatus.PAYMENT_PENDING, OrderStatus.PAYMENT_CONFIRMED, OrderStatus.CANCELLED));
        transitions.put(OrderStatus.PAYMENT_PENDING, EnumSet.of(OrderStatus.PAYMENT_CONFIRMED, OrderStatus.CANCELLED));
        transitions.put(OrderStatus.PAYMENT_CONFIRMED, EnumSet.of(OrderStatus.PROCESSING, OrderStatus.ALLOCATED_TO_WAREHOUSE, OrderStatus.CANCELLED));
        transitions.put(OrderStatus.PROCESSING, EnumSet.of(OrderStatus.ALLOCATED_TO_WAREHOUSE, OrderStatus.CANCELLED));
        transitions.put(OrderStatus.ALLOCATED_TO_WAREHOUSE, EnumSet.of(OrderStatus.PACKED, OrderStatus.CANCELLED));
        transitions.put(OrderStatus.PACKED, EnumSet.of(OrderStatus.SHIPPED, OrderStatus.CANCELLED));
        transitions.put(OrderStatus.SHIPPED, EnumSet.of(OrderStatus.OUT_FOR_DELIVERY, OrderStatus.RETURN_REQUESTED));
        transitions.put(OrderStatus.OUT_FOR_DELIVERY, EnumSet.of(OrderStatus.DELIVERED));
        transitions.put(OrderStatus.DELIVERED, EnumSet.of(OrderStatus.RETURN_REQUESTED, OrderStatus.RETURNED, OrderStatus.REFUNDED));
        transitions.put(OrderStatus.RETURN_REQUESTED, EnumSet.of(OrderStatus.RETURNED, OrderStatus.REFUNDED));
        transitions.put(OrderStatus.RETURNED, EnumSet.of(OrderStatus.REFUNDED));
        transitions.put(OrderStatus.CANCELLED, EnumSet.noneOf(OrderStatus.class));
        transitions.put(OrderStatus.REFUNDED, EnumSet.noneOf(OrderStatus.class));
        return transitions;
    }
}
