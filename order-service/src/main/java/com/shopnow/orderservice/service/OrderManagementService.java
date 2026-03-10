package com.shopnow.orderservice.service;

import com.shopnow.orderapi.dto.OrderResponse;
import com.shopnow.orderapi.dto.PlaceOrderRequest;
import java.util.List;

public interface OrderManagementService {
    OrderResponse placeOrder(PlaceOrderRequest request);

    OrderResponse getOrder(String orderNumber);

    List<OrderResponse> getOrdersByCustomer(String customerCode);
}
