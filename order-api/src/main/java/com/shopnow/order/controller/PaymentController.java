package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.PaymentDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Payments")
@StandardApiResponses
@RequestMapping("/payments")
public interface PaymentController {

    @PostMapping("/validate")
    ResponseEntity<ApiResponse<PaymentDTO>> validatePayment(@Valid @RequestBody PaymentDTO request);

    @GetMapping("/order/{orderId}")
    ResponseEntity<ApiResponse<PaymentDTO>> getPaymentByOrderId(@PathVariable Long orderId);
}
