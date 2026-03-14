package com.shopnow.order.dto;

import com.shopnow.order.enums.ShipmentStatus;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ShipmentDTO {
    private Long id;
    private Long orderId;
    private Long warehouseId;
    private String trackingNumber;
    private String carrier;
    private ShipmentStatus shipmentStatus;
    private LocalDate estimatedDeliveryDate;
}
