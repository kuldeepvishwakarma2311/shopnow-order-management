package com.shopnow.order.repository;

import com.shopnow.order.model.ReturnRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
    Optional<ReturnRequest> findByOrderItemId(Long orderItemId);
    List<ReturnRequest> findAllByOrderByRequestedDateDesc();
}
