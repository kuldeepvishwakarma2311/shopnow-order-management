package com.shopnow.order.model;

import com.shopnow.order.enums.EntityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "WAREHOUSES")
public class Warehouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WAREHOUSE_ID")
    private Long id;

    @Column(name = "WAREHOUSE_NAME", nullable = false)
    private String name;

    @Column(name = "LOCATION", nullable = false)
    private String location;

    @Column(name = "PINCODE", nullable = false, length = 10)
    private String pincode;

    @Column(name = "CAPACITY", nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "WAREHOUSE_STATUS", nullable = false)
    private EntityStatus status;
}
