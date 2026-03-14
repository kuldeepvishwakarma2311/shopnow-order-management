package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.AuthController;
import com.shopnow.order.dto.JwtResponseDTO;
import com.shopnow.order.dto.LoginRequestDTO;
import com.shopnow.order.service.AuthService;
import com.shopnow.order.wrapper.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<ApiResponse<JwtResponseDTO>> login(LoginRequestDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Authentication successful", authService.login(request)));
    }
}
