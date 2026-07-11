package com.ignaciorudyk.resource.auth.dto;

public record UserInfoDTO(
        Long id,
        String email,
        String firstName,
        String lastName,
        String role
) {}