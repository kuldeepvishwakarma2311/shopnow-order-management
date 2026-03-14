package com.shopnow.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryDTO {
    private Long id;
    @NotNull
    private Long productId;
    @NotNull
    private Long warehouseId;
    @NotNull
    @Min(0)
    private Integer stockQuantity;
    @NotNull
    @Min(0)
    private Integer reservedStock;
    @NotNull
    @Min(0)
    private Integer availableStock;
}
