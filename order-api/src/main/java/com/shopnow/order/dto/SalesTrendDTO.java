package com.shopnow.order.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SalesTrendDTO {
    private String sku;
    private String productName;
    private Long totalUnitsSold;
    private BigDecimal grossRevenue;
}
