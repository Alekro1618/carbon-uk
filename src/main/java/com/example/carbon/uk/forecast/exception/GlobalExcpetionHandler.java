package com.example.carbon.uk.forecast.exception;

import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExcpetionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handle(GenerationIsEmpty e) {
        return ResponseEntity.status(404).body(
            new ErrorResponse(
                404, 
                e.getMessage(),
                LocalDate.now()
            )
        );
    }

    @ExceptionHandler
    public ResponseEntity<?> handle(InvalidTimeInterval e) {
        return ResponseEntity.status(403).body(
            new ErrorResponse(
                403, 
                e.getMessage(),
                LocalDate.now()
            )
        );
    }

    @ExceptionHandler
    public ResponseEntity<?> handle(IllegalArgumentException e) {
        return ResponseEntity.status(403).body(
            new ErrorResponse(
                403, 
                e.getMessage(),
                LocalDate.now()
            )
        );
    }
}