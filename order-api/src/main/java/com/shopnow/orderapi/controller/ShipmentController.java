package com.shopnow.orderapi.controller;

import com.shopnow.orderapi.dto.ShipmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/shipments")
public interface ShipmentController {

    @GetMapping("/orders/{orderNumber}")
    @Operation(summary = "Track shipment by order number")
    ShipmentResponse trackShipment(@PathVariable String orderNumber);
}
