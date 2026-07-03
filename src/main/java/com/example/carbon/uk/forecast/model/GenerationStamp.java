package com.example.carbon.uk.forecast.model;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenerationStamp {
    @JsonProperty("from")
    private OffsetDateTime from;

    @JsonProperty("to")
    private OffsetDateTime to;

    @JsonProperty("generationmix")
    private List<Generation> mix;
}

