package com.kidd.shopping.base.response;

import com.kidd.shopping.base.entity.PageResult;
import com.kidd.shopping.utils.ResponseConstant;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

public class OkResponse extends Response {
    public <T> OkResponse(T data) {
        super(HttpStatus.OK, ResponseConstant.MSG_OK, data);
    }

    public OkResponse(Page<?> page) {
        super(HttpStatus.OK, ResponseConstant.MSG_OK, new PageResult(page));
    }

    public OkResponse() {
        super(HttpStatus.OK, ResponseConstant.MSG_OK);
    }
}
