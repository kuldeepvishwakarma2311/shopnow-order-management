package com.shopnow.orderapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record ReturnRequestDto(
        @NotBlank String reasonCode,
        @DecimalMin("0.0") BigDecimal refundAmount) {}
