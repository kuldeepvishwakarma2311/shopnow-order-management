package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.PaymentController;
import com.shopnow.order.dto.PaymentDTO;
import com.shopnow.order.service.PaymentService;
import com.shopnow.order.wrapper.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<ApiResponse<PaymentDTO>> validatePayment(PaymentDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment validated", paymentService.validatePayment(request)));
    }

    @Override
    public ResponseEntity<ApiResponse<PaymentDTO>> getPaymentByOrderId(Long orderId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Payment fetched", paymentService.getPaymentByOrderId(orderId)));
    }
}
