package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.ReturnRequestDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Returns")
@StandardApiResponses
@RequestMapping
public interface ReturnController {

    @PostMapping("/orders/{id}/return")
    ResponseEntity<ApiResponse<ReturnRequestDTO>> createReturnRequest(@PathVariable Long id,
                                                                      @Valid @RequestBody ReturnRequestDTO request);

    @GetMapping("/returns")
    ResponseEntity<ApiResponse<List<ReturnRequestDTO>>> getReturnRequests();
}
