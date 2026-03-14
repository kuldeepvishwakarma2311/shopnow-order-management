package com.shopnow.order.service;

import com.shopnow.order.dto.InventoryDTO;
import java.util.List;

public interface InventoryService {
    List<InventoryDTO> getInventory();
    InventoryDTO updateInventory(InventoryDTO request);
}
