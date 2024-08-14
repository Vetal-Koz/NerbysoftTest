package com.example.nerdysoft_java_test.exception;

public class CanNotBeBorrowedException extends RuntimeException {
    public CanNotBeBorrowedException(String message) {
        super(message);
    }
}
