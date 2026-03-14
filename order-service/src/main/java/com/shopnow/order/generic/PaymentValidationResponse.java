package com.shopnow.order.generic;

import lombok.Data;

@Data
public class PaymentValidationResponse {
    private boolean valid;
    private String gatewayReference;
}
