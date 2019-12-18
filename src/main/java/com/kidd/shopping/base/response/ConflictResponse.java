package com.kidd.shopping.base.response;

import org.springframework.http.HttpStatus;

public class ConflictResponse extends Response {
    public ConflictResponse() {
        super(HttpStatus.CONFLICT, HttpStatus.CONFLICT.getReasonPhrase());
    }

    public ConflictResponse(String msg) {
        super(HttpStatus.CONFLICT, msg);
    }
}
