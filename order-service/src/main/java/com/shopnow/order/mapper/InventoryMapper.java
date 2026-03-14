package com.shopnow.order.mapper;

import com.shopnow.order.dto.InventoryDTO;
import com.shopnow.order.model.Inventory;
public interface InventoryMapper {
    InventoryDTO toDto(Inventory entity);
}
