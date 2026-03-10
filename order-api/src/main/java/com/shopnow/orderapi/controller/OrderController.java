package com.shopnow.orderapi.controller;

import com.shopnow.orderapi.dto.OrderResponse;
import com.shopnow.orderapi.dto.PlaceOrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/orders")
public interface OrderController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place a customer order and reserve inventory")
    OrderResponse placeOrder(@Valid @RequestBody PlaceOrderRequest request);

    @GetMapping("/{orderNumber}")
    @Operation(summary = "Fetch order details by order number")
    OrderResponse getOrder(@PathVariable String orderNumber);
}
