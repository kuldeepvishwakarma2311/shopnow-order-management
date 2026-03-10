package com.shopnow.orderservice.service.impl;

import com.shopnow.orderapi.dto.ReturnRequestDto;
import com.shopnow.orderapi.dto.ReturnResponse;
import com.shopnow.orderapi.enums.OrderStatus;
import com.shopnow.orderapi.enums.PaymentStatus;
import com.shopnow.orderapi.enums.ReturnStatus;
import com.shopnow.orderapi.model.CustomerOrder;
import com.shopnow.orderapi.model.ReturnRequest;
import com.shopnow.orderservice.repository.CustomerOrderRepository;
import com.shopnow.orderservice.repository.ReturnRequestRepository;
import com.shopnow.orderservice.service.ReturnManagementService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReturnManagementServiceImpl implements ReturnManagementService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ReturnRequestRepository returnRequestRepository;
    private final OrderMapper orderMapper;

    public ReturnManagementServiceImpl(
            CustomerOrderRepository customerOrderRepository,
            ReturnRequestRepository returnRequestRepository,
            OrderMapper orderMapper) {
        this.customerOrderRepository = customerOrderRepository;
        this.returnRequestRepository = returnRequestRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public ReturnResponse createReturn(String orderNumber, ReturnRequestDto request) {
        CustomerOrder order = customerOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderNumber));
        ReturnRequest returnRequest = new ReturnRequest();
        returnRequest.setCustomerOrder(order);
        returnRequest.setReasonCode(request.reasonCode());
        returnRequest.setRefundAmount(request.refundAmount());
        returnRequest.setReturnStatus(ReturnStatus.REFUND_INITIATED);

        order.setOrderStatus(OrderStatus.RETURN_REQUESTED);
        order.setPaymentStatus(PaymentStatus.REFUNDED);

        return orderMapper.toReturnResponse(returnRequestRepository.save(returnRequest));
    }
}
