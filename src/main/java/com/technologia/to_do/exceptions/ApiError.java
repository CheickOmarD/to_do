package com.technologia.to_do.exceptions;

public class ApiError extends RuntimeException{
    public ApiError(String message) {
        super(message);
    }
}
