package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.CustomerController;
import com.shopnow.order.dto.CustomerDTO;
import com.shopnow.order.dto.OrderResponseDTO;
import com.shopnow.order.service.CustomerService;
import com.shopnow.order.service.OrderService;
import com.shopnow.order.wrapper.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    @Override
    public ResponseEntity<ApiResponse<CustomerDTO>> createCustomer(CustomerDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer created", customerService.createCustomer(request)));
    }

    @Override
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getCustomers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Customers fetched", customerService.getCustomers()));
    }

    @Override
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getCustomerOrders(Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer orders fetched", orderService.getCustomerOrders(id)));
    }
}
