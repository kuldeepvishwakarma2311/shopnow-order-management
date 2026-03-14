package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.OrderController;
import com.shopnow.order.dto.OrderRequestDTO;
import com.shopnow.order.dto.OrderResponseDTO;
import com.shopnow.order.dto.OrderStatusUpdateDTO;
import com.shopnow.order.service.OrderService;
import com.shopnow.order.wrapper.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(OrderRequestDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Order created", orderService.createOrder(request)));
    }

    @Override
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrderById(Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Order fetched", orderService.getOrderById(id)));
    }

    @Override
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getOrders() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Orders fetched", orderService.getOrders()));
    }

    @Override
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrderStatus(Long id, OrderStatusUpdateDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Order status updated",
            orderService.updateOrderStatus(id, request.getStatus())));
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> cancelOrder(Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order cancelled", null));
    }
}
