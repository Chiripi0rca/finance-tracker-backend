package com.finance_tracker.finance_tracker_backend.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> ResourceNotFoundException(ResourceNotFoundException ex){
        Map<String, String> error = Map.of("error", ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String mensaje = ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();
        Map<String, String> error = Map.of("error", mensaje);
        return ResponseEntity.status(400).body(error);
    }
}
