package com.shopnow.order.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderLineDTO {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
