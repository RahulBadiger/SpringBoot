package com.spring.demo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
    private String message;
    private int status;
    private Object data;

    public CustomResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public CustomResponse
            (String message, int status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
