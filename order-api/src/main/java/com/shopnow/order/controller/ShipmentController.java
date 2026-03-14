package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.ShipmentDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Shipments")
@StandardApiResponses
@RequestMapping("/shipment")
public interface ShipmentController {

    @GetMapping("/{trackingNumber}")
    ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentByTrackingNumber(@PathVariable String trackingNumber);

    @GetMapping("/order/{orderId}")
    ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentByOrderId(@PathVariable Long orderId);
}
