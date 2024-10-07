package com.technologia.to_do.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizeException  extends RuntimeException{

    private HttpStatus status = HttpStatus.UNAUTHORIZED;
    private String message;

}
