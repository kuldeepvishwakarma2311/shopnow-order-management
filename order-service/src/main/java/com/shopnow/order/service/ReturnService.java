package com.shopnow.order.service;

import com.shopnow.order.dto.ReturnRequestDTO;
import java.util.List;

public interface ReturnService {
    ReturnRequestDTO createReturnRequest(Long orderId, ReturnRequestDTO request);
    List<ReturnRequestDTO> getReturnRequests();
}
