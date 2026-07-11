package com.ignaciorudyk.resource.auth.service;

import com.ignaciorudyk.resource.auth.dto.UserInfoDTO;

public interface JwtService {

    boolean isTokenValid(String token);

    UserInfoDTO extractClaims(String token);

}
