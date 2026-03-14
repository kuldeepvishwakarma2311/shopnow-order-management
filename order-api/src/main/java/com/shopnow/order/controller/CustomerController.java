package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.CustomerDTO;
import com.shopnow.order.dto.OrderResponseDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Customers")
@StandardApiResponses
@RequestMapping
public interface CustomerController {

    @PostMapping("/customers")
    ResponseEntity<ApiResponse<CustomerDTO>> createCustomer(@Valid @RequestBody CustomerDTO request);

    @GetMapping("/customers")
    ResponseEntity<ApiResponse<List<CustomerDTO>>> getCustomers();

    @GetMapping("/customer/{id}/orders")
    ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getCustomerOrders(@PathVariable Long id);
}
