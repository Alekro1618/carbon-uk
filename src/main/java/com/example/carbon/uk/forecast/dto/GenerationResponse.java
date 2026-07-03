package com.example.carbon.uk.forecast.dto;

import java.util.List;

import com.example.carbon.uk.forecast.model.Generation;
import com.example.carbon.uk.forecast.model.GenerationStamp;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenerationResponse {
    @JsonProperty("data")
    private List<GenerationStamp> data;
}
