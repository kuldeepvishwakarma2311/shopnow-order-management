package com.shopnow.order.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProviderService {
    String generateToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    long getExpirationSeconds();
}
