package com.shopnow.orderservice.controller.impl;

import com.shopnow.orderapi.controller.ShipmentController;
import com.shopnow.orderapi.dto.ShipmentResponse;
import com.shopnow.orderservice.service.ShipmentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShipmentControllerImpl implements ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentControllerImpl(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Override
    public ShipmentResponse trackShipment(String orderNumber) {
        return shipmentService.trackShipment(orderNumber);
    }
}
