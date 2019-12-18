package com.kidd.shopping.base.response;

import com.kidd.shopping.utils.ResponseConstant;
import org.springframework.http.HttpStatus;

public class ResourceExistResponse extends Response {
    public ResourceExistResponse() {
        super(HttpStatus.CONFLICT, ResponseConstant.ErrorMessage.RESOURCE_EXIST);
    }

    public ResourceExistResponse(String msg) {
        super(HttpStatus.CONFLICT, msg);
    }
}
