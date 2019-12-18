package com.kidd.shopping.base.response;

import org.springframework.http.HttpStatus;

public class UnauthorizationResponse<T> extends Response {
    public UnauthorizationResponse(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizationResponse(String message, T data) {
        super(HttpStatus.UNAUTHORIZED, message, data);
    }
}
