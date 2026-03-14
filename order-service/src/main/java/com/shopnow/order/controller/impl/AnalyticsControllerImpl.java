package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.AnalyticsController;
import com.shopnow.order.dto.SalesTrendDTO;
import com.shopnow.order.service.AnalyticsService;
import com.shopnow.order.wrapper.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalyticsControllerImpl implements AnalyticsController {

    private final AnalyticsService analyticsService;

    @Override
    public ResponseEntity<ApiResponse<List<SalesTrendDTO>>> getSalesTrends() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Sales trends fetched", analyticsService.getSalesTrends()));
    }
}
