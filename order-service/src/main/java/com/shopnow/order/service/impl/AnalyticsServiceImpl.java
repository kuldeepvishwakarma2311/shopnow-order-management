package com.shopnow.order.service.impl;

import com.shopnow.order.dto.SalesTrendDTO;
import com.shopnow.order.repository.OrderItemRepository;
import com.shopnow.order.service.AnalyticsService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SalesTrendDTO> getSalesTrends() {
        Map<Long, SalesTrendDTO> aggregated = new LinkedHashMap<>();
        orderItemRepository.findAllWithProduct().forEach(item -> {
            SalesTrendDTO dto = aggregated.computeIfAbsent(item.getProduct().getId(), id -> {
                SalesTrendDTO trend = new SalesTrendDTO();
                trend.setSku(item.getProduct().getSku());
                trend.setProductName(item.getProduct().getName());
                trend.setTotalUnitsSold(0L);
                trend.setGrossRevenue(java.math.BigDecimal.ZERO);
                return trend;
            });
            dto.setTotalUnitsSold(dto.getTotalUnitsSold() + item.getQuantity());
            dto.setGrossRevenue(dto.getGrossRevenue().add(item.getSubtotal()));
        });
        return List.copyOf(aggregated.values());
    }
}
