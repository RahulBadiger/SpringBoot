package com.spring.demo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
    private String message;
    private int status_code;
    private Object data;

    public CustomResponse(String message, int status_code) {
        this.message = message;
        this.status_code = status_code;
    }

    public CustomResponse(String message, int status_code, Object data) {
        this.message = message;
        this.status_code = status_code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public Object getData() {
        return data;
    }
}
