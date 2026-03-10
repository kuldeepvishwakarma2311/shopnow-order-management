package com.shopnow.orderapi.controller;

import com.shopnow.orderapi.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/customers")
public interface CustomerOrderQueryController {

    @GetMapping("/{customerCode}/orders")
    @Operation(summary = "List customer order history")
    List<OrderResponse> getOrderHistory(@PathVariable String customerCode);
}
