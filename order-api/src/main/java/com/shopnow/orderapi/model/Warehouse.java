package com.shopnow.orderapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Warehouse extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String warehouseCode;

    @Column(nullable = false)
    private String warehouseName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Integer routePriority;

    @Column(nullable = false)
    private boolean active;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getRoutePriority() {
        return routePriority;
    }

    public void setRoutePriority(Integer routePriority) {
        this.routePriority = routePriority;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
