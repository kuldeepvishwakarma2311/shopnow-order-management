package com.shopnow.orderservice.service.impl;

import com.shopnow.orderapi.dto.SalesTrendView;
import com.shopnow.orderapi.model.CustomerOrder;
import com.shopnow.orderapi.model.OrderItem;
import com.shopnow.orderservice.repository.CustomerOrderRepository;
import com.shopnow.orderservice.service.AnalyticsService;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AnalyticsServiceImpl implements AnalyticsService {

    private final CustomerOrderRepository customerOrderRepository;

    public AnalyticsServiceImpl(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public List<SalesTrendView> getSalesTrends() {
        Map<String, SalesAccumulator> sales = new LinkedHashMap<>();
        for (CustomerOrder order : customerOrderRepository.findAll()) {
            for (OrderItem item : order.getItems()) {
                sales.computeIfAbsent(item.getProduct().getSku(),
                                key -> new SalesAccumulator(item.getProduct().getProductName()))
                        .add(item.getQuantity(), item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }
        return sales.entrySet().stream()
                .map(entry -> new SalesTrendView(
                        entry.getKey(),
                        entry.getValue().productName(),
                        entry.getValue().totalUnitsSold(),
                        entry.getValue().totalRevenue()))
                .toList();
    }

    private static final class SalesAccumulator {
        private final String productName;
        private long totalUnitsSold;
        private BigDecimal totalRevenue = BigDecimal.ZERO;

        private SalesAccumulator(String productName) {
            this.productName = productName;
        }

        private void add(int quantity, BigDecimal revenue) {
            totalUnitsSold += quantity;
            totalRevenue = totalRevenue.add(revenue);
        }

        private String productName() {
            return productName;
        }

        private long totalUnitsSold() {
            return totalUnitsSold;
        }

        private BigDecimal totalRevenue() {
            return totalRevenue;
        }
    }
}
