package com.shopnow.orderservice.repository;

import com.shopnow.orderapi.model.Shipment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByCustomerOrderOrderNumber(String orderNumber);
}
