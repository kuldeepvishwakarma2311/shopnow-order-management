package com.shopnow.orderservice.service;

import com.shopnow.orderapi.dto.SalesTrendView;
import java.util.List;

public interface AnalyticsService {
    List<SalesTrendView> getSalesTrends();
}
