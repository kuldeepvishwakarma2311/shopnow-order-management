package com.shopnow.orderservice.service.impl;

import com.shopnow.orderapi.dto.OrderItemRequest;
import com.shopnow.orderapi.dto.OrderResponse;
import com.shopnow.orderapi.dto.PlaceOrderRequest;
import com.shopnow.orderapi.enums.OrderStatus;
import com.shopnow.orderapi.enums.PaymentStatus;
import com.shopnow.orderapi.model.Customer;
import com.shopnow.orderapi.model.CustomerOrder;
import com.shopnow.orderapi.model.OrderItem;
import com.shopnow.orderapi.model.Product;
import com.shopnow.orderapi.model.Warehouse;
import com.shopnow.orderservice.repository.CustomerOrderRepository;
import com.shopnow.orderservice.repository.CustomerRepository;
import com.shopnow.orderservice.service.InventoryService;
import com.shopnow.orderservice.service.OrderManagementService;
import com.shopnow.orderservice.service.ShipmentService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderManagementServiceImpl implements OrderManagementService {

    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final InventoryService inventoryService;
    private final InventoryServiceImpl inventoryServiceImpl;
    private final ShipmentService shipmentService;
    private final OrderMapper orderMapper;

    public OrderManagementServiceImpl(
            CustomerRepository customerRepository,
            CustomerOrderRepository customerOrderRepository,
            InventoryService inventoryService,
            InventoryServiceImpl inventoryServiceImpl,
            ShipmentService shipmentService,
            OrderMapper orderMapper) {
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.inventoryService = inventoryService;
        this.inventoryServiceImpl = inventoryServiceImpl;
        this.shipmentService = shipmentService;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {
        Customer customer = customerRepository.findByCustomerCode(request.customerCode())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + request.customerCode()));
        Map<Product, Integer> requestedProducts = inventoryServiceImpl.loadRequestedProducts(request.items());
        Warehouse warehouse = inventoryService.selectWarehouse(request.items());
        inventoryService.reserveInventory(warehouse, requestedProducts);

        CustomerOrder order = new CustomerOrder();
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setCustomer(customer);
        order.setOrderStatus(OrderStatus.ROUTED);
        order.setPaymentStatus(PaymentStatus.AUTHORIZED);
        order.setShippingAddress(request.shippingAddress());
        order.setFulfillmentWarehouseCode(warehouse.getWarehouseCode());
        order.setOrderTotal(calculateOrderTotal(requestedProducts));

        for (OrderItemRequest itemRequest : request.items()) {
            Product product = requestedProducts.keySet().stream()
                    .filter(candidate -> candidate.getSku().equals(itemRequest.sku()))
                    .findFirst()
                    .orElseThrow();
            OrderItem orderItem = new OrderItem();
            orderItem.setCustomerOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.quantity());
            orderItem.setUnitPrice(product.getUnitPrice());
            order.getItems().add(orderItem);
        }

        CustomerOrder persistedOrder = customerOrderRepository.save(order);
        shipmentService.createShipment(persistedOrder);
        return orderMapper.toOrderResponse(persistedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(String orderNumber) {
        CustomerOrder order = customerOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderNumber));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByCustomer(String customerCode) {
        return customerOrderRepository.findByCustomerCustomerCodeOrderByCreatedAtDesc(customerCode).stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    private BigDecimal calculateOrderTotal(Map<Product, Integer> requestedProducts) {
        return requestedProducts.entrySet().stream()
                .map(entry -> entry.getKey().getUnitPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
