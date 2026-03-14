package com.shopnow.order.mapper;

import com.shopnow.order.dto.PaymentDTO;
import com.shopnow.order.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDTO toDto(Payment entity) {
        if (entity == null) {
            return null;
        }
        PaymentDTO dto = new PaymentDTO();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrder().getId());
        dto.setTransactionId(entity.getTransactionId());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setAmount(entity.getAmount());
        dto.setPaymentStatus(entity.getPaymentStatus());
        dto.setPaymentDate(entity.getPaymentDate());
        return dto;
    }
}
