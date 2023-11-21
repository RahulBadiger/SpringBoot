package com.spring.demo.exception;

public class MyEntityException extends Exception {
    private String message;

    public MyEntityException(String message) {
        this.setMessage(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
