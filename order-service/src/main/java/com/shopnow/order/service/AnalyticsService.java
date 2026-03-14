package com.shopnow.order.service;

import com.shopnow.order.dto.SalesTrendDTO;
import java.util.List;

public interface AnalyticsService {
    List<SalesTrendDTO> getSalesTrends();
}
