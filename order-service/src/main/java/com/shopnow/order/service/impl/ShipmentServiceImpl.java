package com.shopnow.order.service.impl;

import com.shopnow.order.dto.ShipmentDTO;
import com.shopnow.order.enums.ShipmentStatus;
import com.shopnow.order.generic.CarrierTrackingResponse;
import com.shopnow.order.generic.ResourceNotFoundException;
import com.shopnow.order.integration.ShippingCarrierClient;
import com.shopnow.order.mapper.ShipmentMapper;
import com.shopnow.order.model.Order;
import com.shopnow.order.model.Shipment;
import com.shopnow.order.model.Warehouse;
import com.shopnow.order.repository.ShipmentRepository;
import com.shopnow.order.service.ShipmentService;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShippingCarrierClient shippingCarrierClient;
    private final ShipmentMapper shipmentMapper;

    @Override
    public Shipment createShipment(Order order, Warehouse warehouse) {
        Shipment shipment = Shipment.builder()
            .order(order)
            .warehouse(warehouse)
            .trackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
            .carrier("ShipNow Logistics")
            .shipmentStatus(ShipmentStatus.READY_FOR_PICKUP)
            .estimatedDeliveryDate(LocalDate.now().plusDays(4))
            .build();
        return shipmentRepository.save(shipment);
    }

    @Override
    public ShipmentDTO getShipmentByTrackingNumber(String trackingNumber) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));
        try {
            CarrierTrackingResponse trackingResponse = shippingCarrierClient.getTracking(trackingNumber);
            shipment.setShipmentStatus(trackingResponse.getShipmentStatus());
            shipment.setEstimatedDeliveryDate(trackingResponse.getEstimatedDeliveryDate());
            shipment = shipmentRepository.save(shipment);
        } catch (Exception ignored) {
            // Use persisted shipment data when carrier integration is unavailable.
        }
        return shipmentMapper.toDto(shipment);
    }

    @Override
    public ShipmentDTO getShipmentByOrderId(Long orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Shipment not found for order"));
        return shipmentMapper.toDto(shipment);
    }
}
