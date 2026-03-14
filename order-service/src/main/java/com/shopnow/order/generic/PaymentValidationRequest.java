package com.shopnow.order.generic;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentValidationRequest {
    private String transactionId;
    private String paymentMethod;
    private BigDecimal amount;
}
