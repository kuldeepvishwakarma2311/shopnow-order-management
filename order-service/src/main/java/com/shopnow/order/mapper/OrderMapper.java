package com.shopnow.order.mapper;

import com.shopnow.order.dto.OrderLineDTO;
import com.shopnow.order.dto.OrderResponseDTO;
import com.shopnow.order.model.Order;
import com.shopnow.order.model.OrderItem;
import com.shopnow.order.model.Shipment;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDTO toDto(Order order, List<OrderItem> items, Shipment shipment) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setPaymentStatus(order.getPaymentStatus());
        if (shipment != null) {
            dto.setAllocatedWarehouseId(shipment.getWarehouse().getId());
            dto.setTrackingNumber(shipment.getTrackingNumber());
        }
        dto.setItems(items.stream().map(this::toLineDto).toList());
        return dto;
    }

    private OrderLineDTO toLineDto(OrderItem item) {
        OrderLineDTO dto = new OrderLineDTO();
        dto.setOrderItemId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }
}
