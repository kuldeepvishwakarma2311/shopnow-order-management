package com.shopnow.orderservice.service.impl;

import com.shopnow.orderapi.dto.OrderLineResponse;
import com.shopnow.orderapi.dto.OrderResponse;
import com.shopnow.orderapi.dto.ReturnResponse;
import com.shopnow.orderapi.dto.ShipmentResponse;
import com.shopnow.orderapi.model.CustomerOrder;
import com.shopnow.orderapi.model.OrderItem;
import com.shopnow.orderapi.model.ReturnRequest;
import com.shopnow.orderapi.model.Shipment;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toOrderResponse(CustomerOrder order) {
        List<OrderLineResponse> items = order.getItems().stream()
                .map(this::toOrderLineResponse)
                .toList();
        return new OrderResponse(
                order.getOrderNumber(),
                order.getCustomer().getCustomerCode(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getFulfillmentWarehouseCode(),
                order.getShippingAddress(),
                order.getOrderTotal(),
                items);
    }

    public ShipmentResponse toShipmentResponse(Shipment shipment) {
        return new ShipmentResponse(
                shipment.getCustomerOrder().getOrderNumber(),
                shipment.getCarrierName(),
                shipment.getTrackingNumber(),
                shipment.getShipmentStatus(),
                shipment.getShippedFromWarehouseCode());
    }

    public ReturnResponse toReturnResponse(ReturnRequest returnRequest) {
        return new ReturnResponse(
                returnRequest.getCustomerOrder().getOrderNumber(),
                returnRequest.getReasonCode(),
                returnRequest.getReturnStatus(),
                returnRequest.getRefundAmount());
    }

    private OrderLineResponse toOrderLineResponse(OrderItem item) {
        return new OrderLineResponse(
                item.getProduct().getSku(),
                item.getProduct().getProductName(),
                item.getQuantity(),
                item.getUnitPrice());
    }
}
