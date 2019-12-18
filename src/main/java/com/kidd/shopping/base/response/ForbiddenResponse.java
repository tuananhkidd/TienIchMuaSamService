package com.kidd.shopping.base.response;

import org.springframework.http.HttpStatus;

public class ForbiddenResponse extends Response {
    public ForbiddenResponse(int code, String msg) {
        super(code, HttpStatus.FORBIDDEN, msg);
    }

    public <T> ForbiddenResponse(int code, String message, T data) {
        super(code, HttpStatus.FORBIDDEN, message, data);
    }

    public ForbiddenResponse(String msg) {
        super(HttpStatus.FORBIDDEN, msg);
    }
}
