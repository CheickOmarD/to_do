package com.technologia.to_do.exceptions;

public class UnauthorizeException  extends RuntimeException{
    public UnauthorizeException(String message) {
        super(message);
    }
}
