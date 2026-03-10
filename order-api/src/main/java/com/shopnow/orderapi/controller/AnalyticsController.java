package com.shopnow.orderapi.controller;

import com.shopnow.orderapi.dto.SalesTrendView;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/analytics")
public interface AnalyticsController {

    @GetMapping("/sales-trends")
    @Operation(summary = "View sales trend summary by product")
    List<SalesTrendView> getSalesTrends();
}
