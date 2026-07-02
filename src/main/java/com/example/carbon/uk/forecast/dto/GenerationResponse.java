package com.example.carbon.uk.forecast.dto;

import java.util.List;

import com.example.carbon.uk.forecast.model.Generation;
import com.example.carbon.uk.forecast.model.GenerationStamp;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerationResponse {
    @JsonProperty("data")
    private List<GenerationStamp> data;
}
