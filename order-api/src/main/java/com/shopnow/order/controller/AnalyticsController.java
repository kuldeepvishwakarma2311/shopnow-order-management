package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.SalesTrendDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Analytics")
@StandardApiResponses
@RequestMapping("/analytics")
public interface AnalyticsController {

    @GetMapping("/sales-trends")
    ResponseEntity<ApiResponse<List<SalesTrendDTO>>> getSalesTrends();
}
