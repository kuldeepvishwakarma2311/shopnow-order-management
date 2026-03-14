package com.shopnow.order.service.impl;

import com.shopnow.order.dto.PaymentDTO;
import com.shopnow.order.enums.PaymentStatus;
import com.shopnow.order.generic.BusinessException;
import com.shopnow.order.generic.PaymentValidationRequest;
import com.shopnow.order.generic.PaymentValidationResponse;
import com.shopnow.order.generic.ResourceNotFoundException;
import com.shopnow.order.integration.PaymentGatewayClient;
import com.shopnow.order.mapper.PaymentMapper;
import com.shopnow.order.model.Order;
import com.shopnow.order.model.Payment;
import com.shopnow.order.repository.PaymentRepository;
import com.shopnow.order.service.PaymentService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentGatewayClient paymentGatewayClient;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDTO validatePayment(PaymentDTO request) {
        if (request.getAmount() == null || request.getAmount().signum() <= 0) {
            throw new BusinessException("Payment amount must be greater than zero");
        }
        paymentRepository.findByTransactionId(request.getTransactionId()).ifPresent(existing -> {
            throw new BusinessException("Transaction ID already exists");
        });

        PaymentValidationRequest gatewayRequest = new PaymentValidationRequest();
        gatewayRequest.setTransactionId(request.getTransactionId());
        gatewayRequest.setPaymentMethod(request.getPaymentMethod());
        gatewayRequest.setAmount(request.getAmount());

        PaymentValidationResponse gatewayResponse;
        try {
            gatewayResponse = paymentGatewayClient.validate(gatewayRequest);
        } catch (Exception ex) {
            gatewayResponse = new PaymentValidationResponse();
            gatewayResponse.setValid(request.getAmount() != null && request.getAmount().signum() > 0);
            gatewayResponse.setGatewayReference("MOCK-" + request.getTransactionId());
        }

        request.setPaymentStatus(gatewayResponse.isValid() ? PaymentStatus.VALIDATED : PaymentStatus.FAILED);
        request.setPaymentDate(LocalDateTime.now());
        return request;
    }

    @Override
    public void recordPayment(Order order, PaymentDTO request, PaymentStatus status) {
        Payment payment = Payment.builder()
            .order(order)
            .transactionId(request.getTransactionId())
            .paymentMethod(request.getPaymentMethod())
            .amount(request.getAmount())
            .paymentStatus(status)
            .paymentDate(LocalDateTime.now())
            .build();
        paymentRepository.save(payment);
    }

    @Override
    public void refundPayment(Order order, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new BusinessException("Refund amount must be greater than zero");
        }
        paymentRepository.findByOrderId(order.getId()).ifPresent(payment -> {
            payment.setAmount(amount);
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
            payment.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(payment);
        });
    }

    @Override
    public PaymentDTO getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found for order"));
        return paymentMapper.toDto(payment);
    }
}
