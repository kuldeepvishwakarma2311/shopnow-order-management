package com.shopnow.orderservice.service;

import com.shopnow.orderapi.dto.ShipmentResponse;
import com.shopnow.orderapi.model.CustomerOrder;

public interface ShipmentService {
    ShipmentResponse createShipment(CustomerOrder customerOrder);

    ShipmentResponse trackShipment(String orderNumber);
}
