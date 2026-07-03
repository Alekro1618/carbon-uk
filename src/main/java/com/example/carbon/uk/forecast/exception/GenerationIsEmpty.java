package com.example.carbon.uk.forecast.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenerationIsEmpty extends RuntimeException {
    public GenerationIsEmpty(String msg) {
        super(msg);
    }
}
