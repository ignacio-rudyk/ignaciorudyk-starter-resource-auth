package com.ignaciorudyk.resource.auth.service;

import com.ignaciorudyk.resource.auth.DTO.JwtClaimsDTO;

public interface JwtService {

    boolean isTokenValid(String token);

    JwtClaimsDTO extractClaims(String token);

}
