package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.ShipmentController;
import com.shopnow.order.dto.ShipmentDTO;
import com.shopnow.order.service.ShipmentService;
import com.shopnow.order.wrapper.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShipmentControllerImpl implements ShipmentController {

    private final ShipmentService shipmentService;

    @Override
    public ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentByTrackingNumber(String trackingNumber) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Shipment fetched",
            shipmentService.getShipmentByTrackingNumber(trackingNumber)));
    }

    @Override
    public ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentByOrderId(Long orderId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Shipment fetched",
            shipmentService.getShipmentByOrderId(orderId)));
    }
}
