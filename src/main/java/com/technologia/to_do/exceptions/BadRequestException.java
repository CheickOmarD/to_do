package com.technologia.to_do.exceptions;

public class BadRequestException  extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
