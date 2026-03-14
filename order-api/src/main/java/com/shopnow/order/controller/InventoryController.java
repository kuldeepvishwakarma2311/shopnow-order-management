package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.InventoryDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Inventory")
@StandardApiResponses
@RequestMapping("/inventory")
public interface InventoryController {

    @GetMapping
    ResponseEntity<ApiResponse<List<InventoryDTO>>> getInventory();

    @PutMapping("/update")
    ResponseEntity<ApiResponse<InventoryDTO>> updateInventory(@Valid @RequestBody InventoryDTO request);
}
