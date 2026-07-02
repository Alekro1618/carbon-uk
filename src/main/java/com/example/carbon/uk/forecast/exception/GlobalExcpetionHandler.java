package com.example.carbon.uk.forecast.exception;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExcpetionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handle(JobNotFoundException e) {
        return ResponseEntity.status(404).body(
            new ErrorResponse(
                404, 
                e.getMessage(),
                LocalDate.now()
            )
        );
    }
}