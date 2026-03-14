package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.JwtResponseDTO;
import com.shopnow.order.dto.LoginRequestDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication")
@StandardApiResponses
@RequestMapping("/auth")
public interface AuthController {

    @PostMapping("/login")
    ResponseEntity<ApiResponse<JwtResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request);
}
