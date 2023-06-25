package com.mancala.exceptions;


import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
