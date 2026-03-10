package com.shopnow.orderapi.dto;

import com.shopnow.orderapi.enums.OrderStatus;
import com.shopnow.orderapi.enums.PaymentStatus;
import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        String orderNumber,
        String customerCode,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus,
        String warehouseCode,
        String shippingAddress,
        BigDecimal orderTotal,
        List<OrderLineResponse> items) {}
