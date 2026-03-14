package com.shopnow.order.dto;

import com.shopnow.order.enums.ReturnStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReturnRequestDTO {
    private Long id;
    @NotNull
    private Long orderId;
    @NotNull
    private Long orderItemId;
    @NotBlank
    private String reason;
    private ReturnStatus status;
    private LocalDateTime requestedDate;
    private BigDecimal refundAmount;
}
