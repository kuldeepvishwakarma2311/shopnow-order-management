package com.shopnow.order.mapper;

import com.shopnow.order.dto.ShipmentDTO;
import com.shopnow.order.model.Shipment;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapperImpl implements ShipmentMapper {

    @Override
    public ShipmentDTO toDto(Shipment entity) {
        if (entity == null) {
            return null;
        }
        ShipmentDTO dto = new ShipmentDTO();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrder().getId());
        dto.setWarehouseId(entity.getWarehouse().getId());
        dto.setTrackingNumber(entity.getTrackingNumber());
        dto.setCarrier(entity.getCarrier());
        dto.setShipmentStatus(entity.getShipmentStatus());
        dto.setEstimatedDeliveryDate(entity.getEstimatedDeliveryDate());
        return dto;
    }
}
