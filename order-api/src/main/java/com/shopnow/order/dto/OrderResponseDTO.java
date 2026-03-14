package com.shopnow.order.dto;

import com.shopnow.order.enums.OrderStatus;
import com.shopnow.order.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private Long customerId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
    private Long allocatedWarehouseId;
    private String trackingNumber;
    private List<OrderLineDTO> items;
}
