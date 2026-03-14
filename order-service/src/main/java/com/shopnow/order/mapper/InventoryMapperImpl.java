package com.shopnow.order.mapper;

import com.shopnow.order.dto.InventoryDTO;
import com.shopnow.order.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapperImpl implements InventoryMapper {

    @Override
    public InventoryDTO toDto(Inventory entity) {
        if (entity == null) {
            return null;
        }
        InventoryDTO dto = new InventoryDTO();
        dto.setId(entity.getId());
        dto.setProductId(entity.getProduct().getId());
        dto.setWarehouseId(entity.getWarehouse().getId());
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setReservedStock(entity.getReservedStock());
        dto.setAvailableStock(entity.getAvailableStock());
        return dto;
    }
}
