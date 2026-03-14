package com.shopnow.order.mapper;

import com.shopnow.order.dto.ShipmentDTO;
import com.shopnow.order.model.Shipment;
public interface ShipmentMapper {
    ShipmentDTO toDto(Shipment entity);
}
