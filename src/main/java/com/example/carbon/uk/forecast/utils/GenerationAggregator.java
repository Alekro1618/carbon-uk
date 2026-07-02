package com.example.carbon.uk.forecast.utils;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.carbon.uk.forecast.model.GenerationStamp;

@Component
public class GenerationAggregator {
    public List<GenerationStamp> aggregate(List<GenerationStamp> list, ChronoUnit by) {
        //TODO: implement
        return List.of();
    }
}
