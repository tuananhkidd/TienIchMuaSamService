package com.kidd.shopping.base.response;

import org.springframework.http.HttpStatus;

public class BadRequestResponse<T> extends Response {
    public BadRequestResponse() {
        super(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    public BadRequestResponse(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }

    public BadRequestResponse(String msg, T data) {
        super(HttpStatus.BAD_REQUEST, msg, data);
    }
}
