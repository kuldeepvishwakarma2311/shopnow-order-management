package com.shopnow.orderservice.service.impl;

import com.shopnow.orderapi.dto.InventoryView;
import com.shopnow.orderapi.dto.OrderItemRequest;
import com.shopnow.orderapi.model.Inventory;
import com.shopnow.orderapi.model.Product;
import com.shopnow.orderapi.model.Warehouse;
import com.shopnow.orderservice.repository.InventoryRepository;
import com.shopnow.orderservice.repository.ProductRepository;
import com.shopnow.orderservice.repository.WarehouseRepository;
import com.shopnow.orderservice.service.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public InventoryServiceImpl(
            InventoryRepository inventoryRepository,
            WarehouseRepository warehouseRepository,
            ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Warehouse selectWarehouse(List<OrderItemRequest> items) {
        List<Warehouse> warehouses = warehouseRepository.findByActiveTrueOrderByRoutePriorityAscWarehouseCodeAsc();
        Map<Product, Integer> requestedProducts = loadRequestedProducts(items);
        return warehouses.stream()
                .filter(warehouse -> canFulfill(warehouse, requestedProducts))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No warehouse can fulfill this order"));
    }

    @Override
    public void reserveInventory(Warehouse warehouse, Map<Product, Integer> requestedQuantities) {
        for (Map.Entry<Product, Integer> entry : requestedQuantities.entrySet()) {
            Inventory inventory = inventoryRepository.findByWarehouseAndProduct(warehouse, entry.getKey())
                    .orElseThrow(() -> new IllegalStateException("Inventory record missing for routed warehouse"));
            if (inventory.getAvailableQuantity() < entry.getValue()) {
                throw new IllegalStateException("Inventory changed during reservation");
            }
            inventory.setAvailableQuantity(inventory.getAvailableQuantity() - entry.getValue());
            inventory.setReservedQuantity(inventory.getReservedQuantity() + entry.getValue());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryView> viewInventory(String sku) {
        List<Inventory> records = sku == null || sku.isBlank()
                ? inventoryRepository.findAll()
                : inventoryRepository.findByProductSkuOrderByWarehouseWarehouseCodeAsc(sku);
        return records.stream()
                .map(inventory -> new InventoryView(
                        inventory.getWarehouse().getWarehouseCode(),
                        inventory.getProduct().getSku(),
                        inventory.getAvailableQuantity(),
                        inventory.getReservedQuantity()))
                .toList();
    }

    @Transactional(readOnly = true)
    public Map<Product, Integer> loadRequestedProducts(List<OrderItemRequest> items) {
        Map<Product, Integer> requestedProducts = new HashMap<>();
        for (OrderItemRequest item : items) {
            Product product = productRepository.findBySku(item.sku())
                    .orElseThrow(() -> new EntityNotFoundException("Unknown SKU: " + item.sku()));
            requestedProducts.merge(product, item.quantity(), Integer::sum);
        }
        return requestedProducts;
    }

    private boolean canFulfill(Warehouse warehouse, Map<Product, Integer> requestedProducts) {
        for (Map.Entry<Product, Integer> entry : requestedProducts.entrySet()) {
            Inventory inventory = inventoryRepository.findByWarehouseAndProduct(warehouse, entry.getKey())
                    .orElse(null);
            if (inventory == null || inventory.getAvailableQuantity() < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
}
