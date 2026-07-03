package com.example.carbon.uk.forecast.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.carbon.uk.forecast.client.GenerationClient;
import com.example.carbon.uk.forecast.dto.GenerationResponse;
import com.example.carbon.uk.forecast.model.GenerationStamp;
import com.example.carbon.uk.forecast.utils.GenerationAggregator;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GenerationServiceTest {
    @MockitoBean
    private GenerationClient generationClient;

    @MockitoBean
    private GenerationAggregator aggregator;

    @Autowired
    private GenerationService generationService;

    @Test
    public void test_getGeneration() {
        OffsetDateTime from = OffsetDateTime.parse("2024-01-01T00:00Z");
        OffsetDateTime to = OffsetDateTime.parse("2024-01-04T00:00Z");

        List<GenerationStamp> raw = List.of(
            new GenerationStamp(from, from.plusHours(1), List.of()),
            new GenerationStamp(from.plusDays(1), from.plusDays(1).plusHours(1), List.of()),
            new GenerationStamp(from.plusDays(2), from.plusDays(2).plusHours(1), List.of())
        );

        when(generationClient.getGenerationRange(from, to))
            .thenReturn(new GenerationResponse(raw));

        when(aggregator.aggregate(raw, 1, ChronoUnit.DAYS))
            .thenReturn(raw.stream());

        List<GenerationStamp> result = generationService.getGenerationRange(from, to);
        System.out.println(result);

        assertEquals(result.size(),3);
        assertEquals(result.get(0).getFrom(), from);
        assertEquals(result.get(1).getFrom(), from.plusDays(1));
        assertEquals(result.get(2).getFrom(), from.plusDays(2));

        verify(aggregator).aggregate(raw, 1, ChronoUnit.DAYS);
    }

    @Test void test_optimalGeneration() {
        OffsetDateTime from = OffsetDateTime.parse("2024-01-01T00:00Z");
        OffsetDateTime to = OffsetDateTime.parse("2024-01-04T00:00Z");

        GenerationStamp optimal = new GenerationStamp(from, from.plusHours(4), List.of());
        List<GenerationStamp> raw = List.of(optimal);

        when(generationClient.getGenerationRange(from, to))
            .thenReturn(new GenerationResponse(raw));

        when(aggregator.optimal(raw, 4, ChronoUnit.HOURS))
            .thenReturn(Optional.of(optimal));

        GenerationStamp result = generationService.getOptimalRange(from, to, 4);

        assertEquals(result, optimal);
        verify(aggregator).optimal(raw, 4, ChronoUnit.HOURS);
    }
}