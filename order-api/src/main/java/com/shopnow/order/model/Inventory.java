package com.shopnow.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(
    name = "INVENTORY",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_INVENTORY_PRODUCT_WAREHOUSE", columnNames = {"PRODUCT_ID", "WAREHOUSE_ID"})
    }
)
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Column(name = "INVENTORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WAREHOUSE_ID")
    private Warehouse warehouse;

    @jakarta.persistence.Column(name = "STOCK_QUANTITY")
    private Integer stockQuantity;

    @jakarta.persistence.Column(name = "RESERVED_STOCK")
    private Integer reservedStock;

    @jakarta.persistence.Column(name = "AVAILABLE_STOCK")
    private Integer availableStock;
}
