package com.shopnow.orderservice.controller.impl;

import com.shopnow.orderapi.controller.InventoryController;
import com.shopnow.orderapi.dto.InventoryView;
import com.shopnow.orderservice.service.InventoryService;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryControllerImpl implements InventoryController {

    private final InventoryService inventoryService;

    public InventoryControllerImpl(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public List<InventoryView> getInventory(String sku) {
        return inventoryService.viewInventory(sku);
    }
}
