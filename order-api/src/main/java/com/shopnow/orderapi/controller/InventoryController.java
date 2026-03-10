package com.shopnow.orderapi.controller;

import com.shopnow.orderapi.dto.InventoryView;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/inventory")
public interface InventoryController {

    @GetMapping
    @Operation(summary = "View inventory across warehouses")
    List<InventoryView> getInventory(@RequestParam(required = false) String sku);
}
