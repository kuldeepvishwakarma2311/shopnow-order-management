package com.shopnow.orderapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Product extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
