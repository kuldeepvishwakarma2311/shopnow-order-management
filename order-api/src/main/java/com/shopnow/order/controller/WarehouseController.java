package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.WarehouseDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Warehouses")
@StandardApiResponses
@RequestMapping("/warehouses")
public interface WarehouseController {

    @GetMapping
    ResponseEntity<ApiResponse<List<WarehouseDTO>>> getWarehouses();

    @PostMapping
    ResponseEntity<ApiResponse<WarehouseDTO>> createWarehouse(@Valid @RequestBody WarehouseDTO request);
}
