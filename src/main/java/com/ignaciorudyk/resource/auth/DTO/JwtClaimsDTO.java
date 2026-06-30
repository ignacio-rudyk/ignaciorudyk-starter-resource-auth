package com.ignaciorudyk.resource.auth.DTO;

public record JwtClaimsDTO(
        Long userId,
        String email,
        String firstName,
        String lastName,
        String role
) {}