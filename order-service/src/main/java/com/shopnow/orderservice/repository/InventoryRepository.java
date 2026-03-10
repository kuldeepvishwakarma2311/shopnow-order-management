package com.shopnow.orderservice.repository;

import com.shopnow.orderapi.model.Inventory;
import com.shopnow.orderapi.model.Product;
import com.shopnow.orderapi.model.Warehouse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductSkuOrderByWarehouseWarehouseCodeAsc(String sku);

    List<Inventory> findByWarehouseInAndProductIn(List<Warehouse> warehouses, List<Product> products);

    Optional<Inventory> findByWarehouseAndProduct(Warehouse warehouse, Product product);
}
