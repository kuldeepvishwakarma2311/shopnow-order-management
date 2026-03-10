package com.shopnow.orderapi.model;

import com.shopnow.orderapi.enums.ShipmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class Shipment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CustomerOrder customerOrder;

    @Column(nullable = false)
    private String carrierName;

    @Column(nullable = false, unique = true)
    private String trackingNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;

    @Column(nullable = false)
    private String shippedFromWarehouseCode;

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getShippedFromWarehouseCode() {
        return shippedFromWarehouseCode;
    }

    public void setShippedFromWarehouseCode(String shippedFromWarehouseCode) {
        this.shippedFromWarehouseCode = shippedFromWarehouseCode;
    }
}
