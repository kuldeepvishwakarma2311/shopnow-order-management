package com.shopnow.orderservice.controller.impl;

import jakarta.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(EntityNotFoundException exception) {
        return Map.of(
                "timestamp", OffsetDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", exception.getMessage());
    }

    @ExceptionHandler({IllegalStateException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(Exception exception) {
        return Map.of(
                "timestamp", OffsetDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", exception.getMessage());
    }
}
