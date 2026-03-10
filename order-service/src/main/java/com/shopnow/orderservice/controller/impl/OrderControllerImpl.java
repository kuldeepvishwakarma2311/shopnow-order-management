package com.shopnow.orderservice.controller.impl;

import com.shopnow.orderapi.controller.OrderController;
import com.shopnow.orderapi.dto.OrderResponse;
import com.shopnow.orderapi.dto.PlaceOrderRequest;
import com.shopnow.orderservice.service.OrderManagementService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerImpl implements OrderController {

    private final OrderManagementService orderManagementService;

    public OrderControllerImpl(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {
        return orderManagementService.placeOrder(request);
    }

    @Override
    public OrderResponse getOrder(String orderNumber) {
        return orderManagementService.getOrder(orderNumber);
    }
}
