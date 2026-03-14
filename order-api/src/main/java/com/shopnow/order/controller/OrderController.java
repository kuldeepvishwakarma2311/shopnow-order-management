package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.OrderRequestDTO;
import com.shopnow.order.dto.OrderResponseDTO;
import com.shopnow.order.dto.OrderStatusUpdateDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Orders")
@StandardApiResponses
@RequestMapping("/orders")
public interface OrderController {

    @PostMapping
    ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(@Valid @RequestBody OrderRequestDTO request);

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<OrderResponseDTO>> getOrderById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getOrders();

    @PutMapping("/{id}/status")
    ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrderStatus(@PathVariable Long id,
                                                                    @Valid @RequestBody OrderStatusUpdateDTO request);

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable Long id);
}
