package com.shopnow.orderservice.controller.impl;

import com.shopnow.orderapi.controller.ReturnController;
import com.shopnow.orderapi.dto.ReturnRequestDto;
import com.shopnow.orderapi.dto.ReturnResponse;
import com.shopnow.orderservice.service.ReturnManagementService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnControllerImpl implements ReturnController {

    private final ReturnManagementService returnManagementService;

    public ReturnControllerImpl(ReturnManagementService returnManagementService) {
        this.returnManagementService = returnManagementService;
    }

    @Override
    public ReturnResponse createReturn(String orderNumber, ReturnRequestDto request) {
        return returnManagementService.createReturn(orderNumber, request);
    }
}
