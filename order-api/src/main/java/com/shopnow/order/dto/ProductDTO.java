package com.shopnow.order.dto;

import com.shopnow.order.enums.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    @NotBlank
    private String sku;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private ProductStatus status;
}
