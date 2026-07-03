package com.example.carbon.uk.forecast.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carbon.uk.forecast.client.GenerationClient;
import com.example.carbon.uk.forecast.model.GenerationStamp;

import lombok.RequiredArgsConstructor;

@SpringBootTest
public class GenerationServiceTest {
    @Autowired
    private GenerationClient generationClient;
    @Autowired
    private GenerationService generationService;

    @Test
    public void test_getGeneration() {
        List<GenerationStamp> result = generationService.getGenerationRange(OffsetDateTime.now(), OffsetDateTime.now().plus(3, ChronoUnit.DAYS));
        System.out.println(result);
        assertTrue(result.size() == 3);
        for (GenerationStamp stamp : result) {
            long difference = ChronoUnit.HOURS.between(stamp.getFrom(), stamp.getTo());
            assertTrue(difference > 1 && difference <= 24);
        }
    }

    @Test void test_optimalGeneration() {
        GenerationStamp result = generationService.getOptimalRange(OffsetDateTime.now(),  OffsetDateTime.now().plus(3, ChronoUnit.DAYS), 4);
        System.out.println(result);
        assertTrue(result != null);
    }
}