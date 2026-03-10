package com.shopnow.orderapi.dto;

import java.math.BigDecimal;

public record SalesTrendView(
        String sku,
        String productName,
        long totalUnitsSold,
        BigDecimal totalRevenue) {}
