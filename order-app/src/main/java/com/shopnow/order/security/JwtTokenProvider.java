package com.shopnow.order.security;

import com.shopnow.order.service.TokenProviderService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProviderService {

    private final SecretKey signingKey;
    private final long expirationSeconds;

    public JwtTokenProvider(
            @Value("${app.security.jwt.secret}") String secret,
            @Value("${app.security.jwt.expiration-seconds}") long expirationSeconds) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationSeconds = expirationSeconds;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        return Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(expirationSeconds)))
            .claim("roles", userDetails.getAuthorities().stream().map(Object::toString).toList())
            .signWith(signingKey)
            .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername())
            && extractClaims(token).getExpiration().after(new Date());
    }

    @Override
    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
