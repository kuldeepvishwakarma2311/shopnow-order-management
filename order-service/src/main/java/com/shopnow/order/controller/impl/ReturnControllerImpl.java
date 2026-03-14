package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.ReturnController;
import com.shopnow.order.dto.ReturnRequestDTO;
import com.shopnow.order.service.ReturnService;
import com.shopnow.order.wrapper.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReturnControllerImpl implements ReturnController {

    private final ReturnService returnService;

    @Override
    public ResponseEntity<ApiResponse<ReturnRequestDTO>> createReturnRequest(Long id, ReturnRequestDTO request) {
        request.setOrderId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Return request created",
            returnService.createReturnRequest(id, request)));
    }

    @Override
    public ResponseEntity<ApiResponse<List<ReturnRequestDTO>>> getReturnRequests() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Return requests fetched", returnService.getReturnRequests()));
    }
}
