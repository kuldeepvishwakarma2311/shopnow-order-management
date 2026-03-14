package com.shopnow.order.service;

import com.shopnow.order.dto.ShipmentDTO;
import com.shopnow.order.model.Order;
import com.shopnow.order.model.Shipment;
import com.shopnow.order.model.Warehouse;

public interface ShipmentService {
    Shipment createShipment(Order order, Warehouse warehouse);
    ShipmentDTO getShipmentByTrackingNumber(String trackingNumber);
    ShipmentDTO getShipmentByOrderId(Long orderId);
}
