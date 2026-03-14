package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.WarehouseController;
import com.shopnow.order.dto.WarehouseDTO;
import com.shopnow.order.service.WarehouseService;
import com.shopnow.order.wrapper.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WarehouseControllerImpl implements WarehouseController {

    private final WarehouseService warehouseService;

    @Override
    public ResponseEntity<ApiResponse<List<WarehouseDTO>>> getWarehouses() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Warehouses fetched", warehouseService.getWarehouses()));
    }

    @Override
    public ResponseEntity<ApiResponse<WarehouseDTO>> createWarehouse(WarehouseDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Warehouse created", warehouseService.createWarehouse(request)));
    }
}
