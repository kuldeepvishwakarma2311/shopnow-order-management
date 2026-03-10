package com.shopnow.orderservice.repository;

import com.shopnow.orderapi.model.CustomerOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    Optional<CustomerOrder> findByOrderNumber(String orderNumber);

    List<CustomerOrder> findByCustomerCustomerCodeOrderByCreatedAtDesc(String customerCode);
}
