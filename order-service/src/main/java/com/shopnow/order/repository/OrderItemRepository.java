package com.shopnow.order.repository;

import com.shopnow.order.model.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select oi from OrderItem oi join fetch oi.product where oi.order.id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    @Query("select oi from OrderItem oi join fetch oi.product")
    List<OrderItem> findAllWithProduct();
}
