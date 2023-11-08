package com.charlie.toggleservice.exceptions;

public class FeatureAlreadyExistsException extends RuntimeException {
    public  FeatureAlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public  FeatureAlreadyExistsException(String message) {
        super(message);
    }

}