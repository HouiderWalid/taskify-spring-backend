package com.taskify.taskifyspringback.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionToJsonValidation {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> messages = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            messages.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.BAD_REQUEST.value());
        body.put("data", new ArrayList<>());
        body.put("messages", messages);

        return ResponseEntity.badRequest().body(body);
    }
}
