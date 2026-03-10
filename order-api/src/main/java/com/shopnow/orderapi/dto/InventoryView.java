package com.shopnow.orderapi.dto;

public record InventoryView(
        String warehouseCode,
        String sku,
        int availableQuantity,
        int reservedQuantity) {}
