package com.shopnow.order.mapper;

import com.shopnow.order.dto.ReturnRequestDTO;
import com.shopnow.order.model.ReturnRequest;
import org.springframework.stereotype.Component;

@Component
public class ReturnRequestMapperImpl implements ReturnRequestMapper {

    @Override
    public ReturnRequestDTO toDto(ReturnRequest entity) {
        if (entity == null) {
            return null;
        }
        ReturnRequestDTO dto = new ReturnRequestDTO();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrder().getId());
        dto.setOrderItemId(entity.getOrderItem().getId());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus());
        dto.setRequestedDate(entity.getRequestedDate());
        dto.setRefundAmount(entity.getRefundAmount());
        return dto;
    }
}
