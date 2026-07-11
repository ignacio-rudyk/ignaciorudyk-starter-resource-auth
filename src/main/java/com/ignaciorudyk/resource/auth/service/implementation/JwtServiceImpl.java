package com.ignaciorudyk.resource.auth.service.implementation;

import com.ignaciorudyk.resource.auth.config.StarterResourceAuthProperties;
import com.ignaciorudyk.resource.auth.dto.UserInfoDTO;
import com.ignaciorudyk.resource.auth.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final StarterResourceAuthProperties starterResourceAuthProperties;

    public JwtServiceImpl(StarterResourceAuthProperties starterResourceAuthProperties) {
        this.starterResourceAuthProperties = starterResourceAuthProperties;
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException e) {
            log.warn("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public UserInfoDTO extractClaims(String token) {
        Claims claims = extractAllClaims(token);
        return new UserInfoDTO(
                claims.get("userId", Long.class),
                claims.getSubject(),
                claims.get("firstName", String.class),
                claims.get("lastName", String.class),
                claims.get("role", String.class)
        );
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(starterResourceAuthProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}