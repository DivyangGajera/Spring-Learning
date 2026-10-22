package com.example.demo.error;

public class NotFoundExecption extends RuntimeException {
    public NotFoundExecption(String message) {
        super(message);
    }

}
