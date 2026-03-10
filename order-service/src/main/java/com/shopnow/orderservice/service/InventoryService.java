package com.shopnow.orderservice.service;

import com.shopnow.orderapi.dto.InventoryView;
import com.shopnow.orderapi.dto.OrderItemRequest;
import com.shopnow.orderapi.model.Product;
import com.shopnow.orderapi.model.Warehouse;
import java.util.List;
import java.util.Map;

public interface InventoryService {
    Warehouse selectWarehouse(List<OrderItemRequest> items);

    void reserveInventory(Warehouse warehouse, Map<Product, Integer> requestedQuantities);

    List<InventoryView> viewInventory(String sku);
}
