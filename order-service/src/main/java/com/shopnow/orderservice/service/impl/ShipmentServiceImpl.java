package com.shopnow.orderservice.service.impl;

import com.shopnow.orderapi.dto.ShipmentResponse;
import com.shopnow.orderapi.enums.ShipmentStatus;
import com.shopnow.orderapi.model.CustomerOrder;
import com.shopnow.orderapi.model.Shipment;
import com.shopnow.orderservice.repository.CustomerOrderRepository;
import com.shopnow.orderservice.repository.ShipmentRepository;
import com.shopnow.orderservice.service.ShipmentService;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final OrderMapper orderMapper;

    public ShipmentServiceImpl(
            ShipmentRepository shipmentRepository,
            CustomerOrderRepository customerOrderRepository,
            OrderMapper orderMapper) {
        this.shipmentRepository = shipmentRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public ShipmentResponse createShipment(CustomerOrder customerOrder) {
        Shipment shipment = new Shipment();
        shipment.setCustomerOrder(customerOrder);
        shipment.setCarrierName("ShopNow Express");
        shipment.setTrackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());
        shipment.setShipmentStatus(ShipmentStatus.CREATED);
        shipment.setShippedFromWarehouseCode(customerOrder.getFulfillmentWarehouseCode());
        return orderMapper.toShipmentResponse(shipmentRepository.save(shipment));
    }

    @Override
    @Transactional(readOnly = true)
    public ShipmentResponse trackShipment(String orderNumber) {
        customerOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderNumber));
        Shipment shipment = shipmentRepository.findByCustomerOrderOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found for order: " + orderNumber));
        return orderMapper.toShipmentResponse(shipment);
    }
}
