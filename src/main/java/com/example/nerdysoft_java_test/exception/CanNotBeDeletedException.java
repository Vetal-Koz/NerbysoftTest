package com.example.nerdysoft_java_test.exception;

public class CanNotBeDeletedException extends RuntimeException {
    public CanNotBeDeletedException(String message) {
        super(message);
    }
}
