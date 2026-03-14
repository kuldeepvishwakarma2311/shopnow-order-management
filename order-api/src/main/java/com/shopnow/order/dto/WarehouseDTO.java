package com.shopnow.order.dto;

import com.shopnow.order.enums.EntityStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WarehouseDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @NotBlank
    private String pincode;
    @NotNull
    private Integer capacity;
    @NotNull
    private EntityStatus status;
}
