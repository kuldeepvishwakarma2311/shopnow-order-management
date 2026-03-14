package com.shopnow.order.service.impl;

import com.shopnow.order.dto.ReturnRequestDTO;
import com.shopnow.order.enums.OrderStatus;
import com.shopnow.order.enums.ReturnStatus;
import com.shopnow.order.generic.BusinessException;
import com.shopnow.order.generic.ResourceNotFoundException;
import com.shopnow.order.mapper.ReturnRequestMapper;
import com.shopnow.order.model.Order;
import com.shopnow.order.model.OrderItem;
import com.shopnow.order.model.ReturnRequest;
import com.shopnow.order.repository.OrderItemRepository;
import com.shopnow.order.repository.OrderRepository;
import com.shopnow.order.repository.ReturnRequestRepository;
import com.shopnow.order.service.PaymentService;
import com.shopnow.order.service.ReturnService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements ReturnService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ReturnRequestRepository returnRequestRepository;
    private final ReturnRequestMapper returnRequestMapper;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public ReturnRequestDTO createReturnRequest(Long orderId, ReturnRequestDTO request) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (order.getStatus() != OrderStatus.DELIVERED && order.getStatus() != OrderStatus.SHIPPED) {
            throw new BusinessException("Return request is only allowed for shipped or delivered orders");
        }

        OrderItem orderItem = orderItemRepository.findById(request.getOrderItemId())
            .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
        if (!orderItem.getOrder().getId().equals(order.getId())) {
            throw new BusinessException("Order item does not belong to the given order");
        }
        returnRequestRepository.findByOrderItemId(orderItem.getId()).ifPresent(existing -> {
            throw new BusinessException("Return request already exists for this order item");
        });

        ReturnRequest returnRequest = ReturnRequest.builder()
            .order(order)
            .orderItem(orderItem)
            .reason(request.getReason())
            .status(ReturnStatus.REQUESTED)
            .requestedDate(LocalDateTime.now())
            .refundAmount(orderItem.getSubtotal())
            .build();

        ReturnRequest saved = returnRequestRepository.save(returnRequest);
        order.setStatus(OrderStatus.RETURN_REQUESTED);
        orderRepository.save(order);
        paymentService.refundPayment(order, saved.getRefundAmount());
        return returnRequestMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReturnRequestDTO> getReturnRequests() {
        return returnRequestRepository.findAllByOrderByRequestedDateDesc().stream()
            .map(returnRequestMapper::toDto)
            .toList();
    }
}
