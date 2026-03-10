package com.shopnow.orderservice.controller.impl;

import com.shopnow.orderapi.controller.AnalyticsController;
import com.shopnow.orderapi.dto.SalesTrendView;
import com.shopnow.orderservice.service.AnalyticsService;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsControllerImpl implements AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsControllerImpl(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @Override
    public List<SalesTrendView> getSalesTrends() {
        return analyticsService.getSalesTrends();
    }
}
