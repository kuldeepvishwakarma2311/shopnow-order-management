package com.shopnow.order.mapper;

import com.shopnow.order.dto.WarehouseDTO;
import com.shopnow.order.model.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapperImpl implements WarehouseMapper {

    @Override
    public WarehouseDTO toDto(Warehouse entity) {
        if (entity == null) {
            return null;
        }
        WarehouseDTO dto = new WarehouseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setPincode(entity.getPincode());
        dto.setCapacity(entity.getCapacity());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public Warehouse toEntity(WarehouseDTO dto) {
        if (dto == null) {
            return null;
        }
        return Warehouse.builder()
            .id(dto.getId())
            .name(dto.getName())
            .location(dto.getLocation())
            .pincode(dto.getPincode())
            .capacity(dto.getCapacity())
            .status(dto.getStatus())
            .build();
    }
}
