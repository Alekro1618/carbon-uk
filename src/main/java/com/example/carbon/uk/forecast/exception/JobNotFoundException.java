package com.example.carbon.uk.forecast.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(String msg) {
        super(msg);
    }
}
