package com.shopnow.orderservice.repository;

import com.shopnow.orderapi.model.ReturnRequest;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
    Optional<ReturnRequest> findByCustomerOrderOrderNumber(String orderNumber);
}
