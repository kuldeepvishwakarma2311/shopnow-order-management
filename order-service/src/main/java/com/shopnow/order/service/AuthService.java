package com.shopnow.order.service;

import com.shopnow.order.dto.JwtResponseDTO;
import com.shopnow.order.dto.LoginRequestDTO;

public interface AuthService {
    JwtResponseDTO login(LoginRequestDTO request);
}
