package com.shopnow.order.integration;

import com.shopnow.order.generic.PaymentValidationRequest;
import com.shopnow.order.generic.PaymentValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentGatewayClient", url = "${integration.payment-gateway.url:http://localhost:9092}")
public interface PaymentGatewayClient {

    @PostMapping("/gateway/validate")
    PaymentValidationResponse validate(@RequestBody PaymentValidationRequest request);
}
