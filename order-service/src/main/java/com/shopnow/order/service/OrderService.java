package com.shopnow.order.service;

import com.shopnow.order.dto.OrderRequestDTO;
import com.shopnow.order.dto.OrderResponseDTO;
import com.shopnow.order.enums.OrderStatus;
import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO request);
    OrderResponseDTO getOrderById(Long id);
    List<OrderResponseDTO> getOrders();
    OrderResponseDTO updateOrderStatus(Long id, OrderStatus status);
    void cancelOrder(Long id);
    List<OrderResponseDTO> getCustomerOrders(Long customerId);
}
