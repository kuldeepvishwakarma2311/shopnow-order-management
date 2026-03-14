package com.shopnow.order.service;

import com.shopnow.order.dto.WarehouseDTO;
import java.util.List;

public interface WarehouseService {
    List<WarehouseDTO> getWarehouses();
    WarehouseDTO createWarehouse(WarehouseDTO request);
}
