package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.InventoryController;
import com.shopnow.order.dto.InventoryDTO;
import com.shopnow.order.service.InventoryService;
import com.shopnow.order.wrapper.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventoryControllerImpl implements InventoryController {

    private final InventoryService inventoryService;

    @Override
    public ResponseEntity<ApiResponse<List<InventoryDTO>>> getInventory() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Inventory fetched", inventoryService.getInventory()));
    }

    @Override
    public ResponseEntity<ApiResponse<InventoryDTO>> updateInventory(InventoryDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Inventory updated", inventoryService.updateInventory(request)));
    }
}
