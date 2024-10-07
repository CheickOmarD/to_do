package com.technologia.to_do.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiError {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
}
