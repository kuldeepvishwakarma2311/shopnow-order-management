package com.shopnow.order.service.impl;

import com.shopnow.order.dto.WarehouseDTO;
import com.shopnow.order.mapper.WarehouseMapper;
import com.shopnow.order.model.Warehouse;
import com.shopnow.order.repository.WarehouseRepository;
import com.shopnow.order.service.WarehouseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public List<WarehouseDTO> getWarehouses() {
        return warehouseRepository.findAll().stream().map(warehouseMapper::toDto).toList();
    }

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO request) {
        Warehouse warehouse = warehouseMapper.toEntity(request);
        return warehouseMapper.toDto(warehouseRepository.save(warehouse));
    }
}
