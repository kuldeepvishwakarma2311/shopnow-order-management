package com.shopnow.orderapi.dto;

import com.shopnow.orderapi.enums.ShipmentStatus;

public record ShipmentResponse(
        String orderNumber,
        String carrierName,
        String trackingNumber,
        ShipmentStatus shipmentStatus,
        String warehouseCode) {}
