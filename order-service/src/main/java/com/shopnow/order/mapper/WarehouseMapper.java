package com.shopnow.order.mapper;

import com.shopnow.order.dto.WarehouseDTO;
import com.shopnow.order.model.Warehouse;
public interface WarehouseMapper {
    WarehouseDTO toDto(Warehouse entity);
    Warehouse toEntity(WarehouseDTO dto);
}
