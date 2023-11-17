package com.spring.demo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
    private String message;
    private int statusCode;
    private Object data;

    public CustomResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public CustomResponse
            (String message, int statusCode, Object data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getData() {
        return data;
    }
}
