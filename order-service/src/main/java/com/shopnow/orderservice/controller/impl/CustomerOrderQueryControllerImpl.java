package com.shopnow.orderservice.controller.impl;

import com.shopnow.orderapi.controller.CustomerOrderQueryController;
import com.shopnow.orderapi.dto.OrderResponse;
import com.shopnow.orderservice.service.OrderManagementService;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerOrderQueryControllerImpl implements CustomerOrderQueryController {

    private final OrderManagementService orderManagementService;

    public CustomerOrderQueryControllerImpl(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }

    @Override
    public List<OrderResponse> getOrderHistory(String customerCode) {
        return orderManagementService.getOrdersByCustomer(customerCode);
    }
}
