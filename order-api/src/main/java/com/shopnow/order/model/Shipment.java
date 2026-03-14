package com.shopnow.order.model;

import com.shopnow.order.enums.ShipmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SHIPMENTS")
public class Shipment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHIPMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WAREHOUSE_ID")
    private Warehouse warehouse;

    @Column(name = "TRACKING_NUMBER", nullable = false, unique = true)
    private String trackingNumber;

    @Column(name = "CARRIER", nullable = false)
    private String carrier;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHIPMENT_STATUS", nullable = false)
    private ShipmentStatus shipmentStatus;

    @Column(name = "ESTIMATED_DELIVERY_DATE")
    private LocalDate estimatedDeliveryDate;
}
