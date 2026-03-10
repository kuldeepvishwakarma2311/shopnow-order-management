package com.shopnow.orderapi.model;

import com.shopnow.orderapi.enums.ReturnStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class ReturnRequest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CustomerOrder customerOrder;

    @Column(nullable = false)
    private String reasonCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReturnStatus returnStatus;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal refundAmount;

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public ReturnStatus getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(ReturnStatus returnStatus) {
        this.returnStatus = returnStatus;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
}
