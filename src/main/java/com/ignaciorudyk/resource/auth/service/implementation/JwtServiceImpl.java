package com.ignaciorudyk.resource.auth.service.implementation;

import com.ignaciorudyk.resource.auth.DTO.JwtClaimsDTO;
import com.ignaciorudyk.resource.auth.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private String secretKey;

    public JwtServiceImpl(@Value("${jwt.secret}")String secretKey) {
        this.secretKey = secretKey;
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
    public JwtClaimsDTO extractClaims(String token) {
        Claims claims = extractAllClaims(token);
        return new JwtClaimsDTO(
                claims.get("userId", Long.class),
                claims.getSubject(),                    // email
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
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}