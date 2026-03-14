package com.shopnow.order.dto;

import com.shopnow.order.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusUpdateDTO {
    @NotNull
    private OrderStatus status;
}
