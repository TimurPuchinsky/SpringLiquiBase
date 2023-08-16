package com.example.springliquidbase;

public class MyCustomException extends RuntimeException {

    public MyCustomException(String message) {
        super(message);
    }
}