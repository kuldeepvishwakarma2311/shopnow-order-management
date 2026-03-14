package com.shopnow.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @NotNull
    private Long customerId;

    @NotEmpty
    @Valid
    private List<OrderItemRequestDTO> items;

    @NotNull
    private PaymentDTO payment;
}
