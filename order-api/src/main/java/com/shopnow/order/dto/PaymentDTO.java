package com.shopnow.order.dto;

import com.shopnow.order.enums.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PaymentDTO {
    private Long id;
    private Long orderId;
    @NotBlank
    private String transactionId;
    @NotBlank
    private String paymentMethod;
    @NotNull
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
}
