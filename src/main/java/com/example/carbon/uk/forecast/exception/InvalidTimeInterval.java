package com.example.carbon.uk.forecast.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidTimeInterval extends RuntimeException {
    public InvalidTimeInterval(String msg) {
        super(msg);
    }
}
