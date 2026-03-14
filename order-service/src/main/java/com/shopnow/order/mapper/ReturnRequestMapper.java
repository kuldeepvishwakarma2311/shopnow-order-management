package com.shopnow.order.mapper;

import com.shopnow.order.dto.ReturnRequestDTO;
import com.shopnow.order.model.ReturnRequest;
public interface ReturnRequestMapper {
    ReturnRequestDTO toDto(ReturnRequest entity);
}
