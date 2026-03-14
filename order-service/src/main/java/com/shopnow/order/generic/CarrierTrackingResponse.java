package com.shopnow.order.generic;

import com.shopnow.order.enums.ShipmentStatus;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CarrierTrackingResponse {
    private String trackingNumber;
    private ShipmentStatus shipmentStatus;
    private LocalDate estimatedDeliveryDate;
}
