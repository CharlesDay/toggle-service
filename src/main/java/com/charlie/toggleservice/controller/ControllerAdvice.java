package com.charlie.toggleservice.controller;

import com.charlie.toggleservice.exceptions.FeatureAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(FeatureAlreadyExistsException.class)
    protected ResponseEntity<?> handleFeatureAlreadyExistsException(FeatureAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
