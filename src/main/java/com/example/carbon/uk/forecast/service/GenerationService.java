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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenerationService {
    private final GenerationClient client;

    public List<GenerationStamp> getGenerationRange(OffsetDateTime from, OffsetDateTime to) {
        return client.getGenerationRange(from, to).getData();
    }

    public GenerationStamp getOptimalRange(int window) {
        List<GenerationStamp> list = client.getGenerationRange(OffsetDateTime.now(), OffsetDateTime.now().plus(2, ChronoUnit.DAYS)).getData();
        //TODO: REMAKE
        return list.getFirst();
    }
}
