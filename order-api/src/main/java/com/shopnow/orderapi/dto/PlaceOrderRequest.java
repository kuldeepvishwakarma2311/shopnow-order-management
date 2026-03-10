package com.shopnow.orderapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record PlaceOrderRequest(
        @NotBlank String customerCode,
        @NotBlank String shippingAddress,
        @NotBlank String paymentReference,
        @Valid @NotEmpty List<OrderItemRequest> items) {}
