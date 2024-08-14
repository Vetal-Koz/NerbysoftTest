package com.example.nerdysoft_java_test.exception;

public class NotValidDataException extends RuntimeException {
    public NotValidDataException(String message) {
        super(message);
    }
}
