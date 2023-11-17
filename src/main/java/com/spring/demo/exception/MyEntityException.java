package com.spring.demo.exception;

import org.springframework.http.HttpStatus;

public class MyEntityException extends Exception{
    public MyEntityException(String message) {
        super(message);
    }
}
