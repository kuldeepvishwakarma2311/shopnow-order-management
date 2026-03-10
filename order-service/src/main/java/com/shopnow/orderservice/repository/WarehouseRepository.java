package com.shopnow.orderservice.repository;

import com.shopnow.orderapi.model.Warehouse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findByActiveTrueOrderByRoutePriorityAscWarehouseCodeAsc();
}
