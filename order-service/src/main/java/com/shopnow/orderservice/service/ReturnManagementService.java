package com.shopnow.orderservice.service;

import com.shopnow.orderapi.dto.ReturnRequestDto;
import com.shopnow.orderapi.dto.ReturnResponse;

public interface ReturnManagementService {
    ReturnResponse createReturn(String orderNumber, ReturnRequestDto request);
}
