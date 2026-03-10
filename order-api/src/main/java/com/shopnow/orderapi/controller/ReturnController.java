package com.shopnow.orderapi.controller;

import com.shopnow.orderapi.dto.ReturnRequestDto;
import com.shopnow.orderapi.dto.ReturnResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/returns")
public interface ReturnController {

    @PostMapping("/orders/{orderNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a return and trigger refund workflow")
    ReturnResponse createReturn(@PathVariable String orderNumber, @Valid @RequestBody ReturnRequestDto request);
}
