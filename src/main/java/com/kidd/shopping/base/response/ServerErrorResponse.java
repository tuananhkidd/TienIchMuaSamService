package com.kidd.shopping.base.response;

import com.kidd.shopping.utils.ResponseConstant;
import org.springframework.http.HttpStatus;

public class ServerErrorResponse extends Response {
    public <T> ServerErrorResponse() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ResponseConstant.ErrorMessage.INTERNAL_SERVER_ERROR);
    }
}
