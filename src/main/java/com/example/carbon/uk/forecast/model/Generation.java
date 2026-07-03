package com.example.carbon.uk.forecast.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Generation {
    @JsonProperty("fuel")
    private String fuel;
    
    @JsonProperty("perc")
    private double percentage;
}
