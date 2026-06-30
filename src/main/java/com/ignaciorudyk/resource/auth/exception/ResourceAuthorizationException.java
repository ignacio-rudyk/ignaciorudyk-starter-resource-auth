package com.ignaciorudyk.resource.auth.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResourceAuthorizationException extends RuntimeException {

    private int code;

    private HttpStatus httpStatus;

    private static final String RESOURCE_AUTHORIZATION_EXCEPTION_MSG = "Hubo un error en la operacion";

    public ResourceAuthorizationException(String message, int code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public ResourceAuthorizationException(String message, int code) {
        this(message, code, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResourceAuthorizationException(String message) {
        this(message, 1);
    }

    public ResourceAuthorizationException() {
        this(RESOURCE_AUTHORIZATION_EXCEPTION_MSG);
    }

}