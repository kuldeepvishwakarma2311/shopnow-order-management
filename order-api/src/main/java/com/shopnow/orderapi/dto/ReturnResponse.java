package com.shopnow.orderapi.dto;

import com.shopnow.orderapi.enums.ReturnStatus;
import java.math.BigDecimal;

public record ReturnResponse(
        String orderNumber,
        String reasonCode,
        ReturnStatus returnStatus,
        BigDecimal refundAmount) {}
