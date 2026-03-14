package com.shopnow.order.service.impl;

import com.shopnow.order.dto.InventoryDTO;
import com.shopnow.order.generic.BusinessException;
import com.shopnow.order.generic.ResourceNotFoundException;
import com.shopnow.order.mapper.InventoryMapper;
import com.shopnow.order.model.Inventory;
import com.shopnow.order.model.Product;
import com.shopnow.order.model.Warehouse;
import com.shopnow.order.repository.InventoryRepository;
import com.shopnow.order.repository.ProductRepository;
import com.shopnow.order.repository.WarehouseRepository;
import com.shopnow.order.service.InventoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public List<InventoryDTO> getInventory() {
        return inventoryRepository.findAll().stream().map(inventoryMapper::toDto).toList();
    }

    @Override
    public InventoryDTO updateInventory(InventoryDTO request) {
        validateInventoryQuantities(request);

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
            .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        Inventory inventory = inventoryRepository.findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
            .orElseGet(Inventory::new);
        inventory.setProduct(product);
        inventory.setWarehouse(warehouse);
        inventory.setStockQuantity(request.getStockQuantity());
        inventory.setReservedStock(request.getReservedStock());
        inventory.setAvailableStock(request.getStockQuantity() - request.getReservedStock());

        return inventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    private void validateInventoryQuantities(InventoryDTO request) {
        if (request.getReservedStock() > request.getStockQuantity()) {
            throw new BusinessException("Reserved stock cannot exceed stock quantity");
        }
        Integer derivedAvailableStock = request.getStockQuantity() - request.getReservedStock();
        if (!derivedAvailableStock.equals(request.getAvailableStock())) {
            throw new BusinessException("Available stock must be equal to stock quantity minus reserved stock");
        }
    }
}
