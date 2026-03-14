package com.shopnow.order.model;

import com.shopnow.order.enums.ReturnStatus;
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
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    name = "RETURN_REQUESTS",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_RETURN_REQUEST_ORDER_ITEM", columnNames = {"ORDER_ITEM_ID"})
    }
)
public class ReturnRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RETURN_REQUEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ITEM_ID")
    private OrderItem orderItem;

    @Column(name = "REASON", nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "RETURN_STATUS", nullable = false)
    private ReturnStatus status;

    @Column(name = "REQUESTED_DATE", nullable = false)
    private LocalDateTime requestedDate;

    @Column(name = "REFUND_AMOUNT", nullable = false, precision = 12, scale = 2)
    private BigDecimal refundAmount;
}
