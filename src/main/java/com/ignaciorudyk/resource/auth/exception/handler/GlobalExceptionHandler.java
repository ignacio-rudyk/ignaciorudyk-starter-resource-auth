package com.ignaciorudyk.resource.auth.exception.handler;

import com.ignaciorudyk.resource.auth.DTO.response.ResponseDTO;
import com.ignaciorudyk.resource.auth.exception.InvalidTokenException;
import com.ignaciorudyk.resource.auth.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Token inválido o expirado
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ResponseDTO> handleInvalidToken(InvalidTokenException ex, HttpServletRequest request) {
        return HttpUtil.isFailureRequestResponse(request, List.of(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Sin autorización
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponseDTO> handleAccessDenied(AuthorizationDeniedException ex, HttpServletRequest request) {
        return HttpUtil.isFailureRequestResponse(request, List.of("No tenés permisos para acceder a este recurso"), HttpStatus.FORBIDDEN);
    }

}