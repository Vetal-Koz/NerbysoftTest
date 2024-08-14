package com.example.nerdysoft_java_test.exception;

import com.example.nerdysoft_java_test.dto.response.ErrorContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleEntityNotFoundDataException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorContainer(Collections.singletonList(exception.getMessage()))
        );
    }

    @ExceptionHandler
    public ResponseEntity<?> handleCanNotBeBorrowedException(CanNotBeBorrowedException exception) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ErrorContainer(Collections.singletonList(exception.getMessage()))
        );
    }

    @ExceptionHandler
    public ResponseEntity<?> handleCanNotBeDeletedException(CanNotBeDeletedException exception) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ErrorContainer(Collections.singletonList(exception.getMessage()))
        );
    }

    @ExceptionHandler
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getFieldErrors()
                .stream()
                .map(e -> e.getField() + " " + e.getDefaultMessage())
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorContainer(errors)
        );
    }
}
