package com.shopnow.order.service.impl;

import com.shopnow.order.dto.JwtResponseDTO;
import com.shopnow.order.dto.LoginRequestDTO;
import com.shopnow.order.service.AuthService;
import com.shopnow.order.service.TokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProviderService tokenProviderService;

    @Override
    public JwtResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new JwtResponseDTO(
            tokenProviderService.generateToken(userDetails),
            "Bearer",
            tokenProviderService.getExpirationSeconds()
        );
    }
}
