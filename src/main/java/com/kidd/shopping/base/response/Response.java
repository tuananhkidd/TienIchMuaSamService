package com.kidd.shopping.base.response;

import com.kidd.shopping.utils.HeaderConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;

public class Response extends ResponseEntity<Response.ResponseBody> implements Serializable {

    public <T> Response(HttpStatus httpStatusCode, String message, T data) {
        super(new ResponseBody<T>(httpStatusCode.value(), message, data), prepareHeader(), httpStatusCode);
    }

    public Response(HttpStatus httpStatusCode, String message) {
        this(httpStatusCode, message, null);
    }

    public <T> Response(int specialResponseCode, HttpStatus httpStatusCode, String message, T data) {
        super(new ResponseBody<T>(specialResponseCode, message, data), prepareHeader(), httpStatusCode);
    }

    public Response(int specialResponseCode, HttpStatus httpStatusCode, String message) {
        this(specialResponseCode, httpStatusCode, message, null);
    }

    public static MultiValueMap<String, String> prepareHeader() {
        MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
        httpHeaders.add(HeaderConstant.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        httpHeaders.add(HeaderConstant.ACCESS_CONTROL_ALLOW_HEADERS, HeaderConstant.CONTENT_TYPE + "," + HeaderConstant.X_REQUESTED_WITH+","+ HeaderConstant.AUTHORIZATION);
        httpHeaders.add(HeaderConstant.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE,OPTIONS");
        httpHeaders.add(HeaderConstant.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        return httpHeaders;
    }

    public static class ResponseBody<T> {
        private int code;
        private String msg;
        private T data;

        public ResponseBody(int code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public ResponseBody(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
