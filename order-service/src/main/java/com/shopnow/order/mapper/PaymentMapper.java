package com.shopnow.order.mapper;

import com.shopnow.order.dto.PaymentDTO;
import com.shopnow.order.model.Payment;
public interface PaymentMapper {
    PaymentDTO toDto(Payment entity);
}
