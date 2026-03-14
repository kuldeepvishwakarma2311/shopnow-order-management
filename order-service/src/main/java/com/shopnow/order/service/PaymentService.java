package com.shopnow.order.service;

import com.shopnow.order.dto.PaymentDTO;
import com.shopnow.order.enums.PaymentStatus;
import com.shopnow.order.model.Order;
import java.math.BigDecimal;

public interface PaymentService {
    PaymentDTO validatePayment(PaymentDTO request);
    void recordPayment(Order order, PaymentDTO request, PaymentStatus status);
    void refundPayment(Order order, BigDecimal amount);
    PaymentDTO getPaymentByOrderId(Long orderId);
}
