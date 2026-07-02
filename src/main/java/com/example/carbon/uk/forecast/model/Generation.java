package com.example.carbon.uk.forecast.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Generation {
    @JsonProperty("fuel")
    private String fuel;
    
    @JsonProperty("perc")
    private float percentage;
}
