package com.example.carbon.uk.forecast.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.carbon.uk.forecast.client.GenerationClient;
import com.example.carbon.uk.forecast.dto.GenerationResponse;
import com.example.carbon.uk.forecast.model.Generation;
import com.example.carbon.uk.forecast.model.GenerationStamp;
import com.example.carbon.uk.forecast.utils.GenerationAggregator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenerationService {
    private final GenerationClient client;
    private final GenerationAggregator aggregator;

    public List<GenerationStamp> getGenerationRange(OffsetDateTime from, OffsetDateTime to) {
        //Get generatation data from parameters
        List<GenerationStamp> list = client.getGenerationRange(from, to).getData();

        //Aggregate generation half hour stamps to day stamps
        return aggregator.aggregate(list, 1, ChronoUnit.DAYS).toList();
            
    }

    public GenerationStamp getOptimalRange(OffsetDateTime from, OffsetDateTime to, int window) {
        //Get generation data from parameters
        List<GenerationStamp> list = client.getGenerationRange(from, to).getData();
        
        //Finds optimal interval with highest eko-friendly energy generation
        return aggregator.optimal(list, window, ChronoUnit.HOURS).get();
    }
}
