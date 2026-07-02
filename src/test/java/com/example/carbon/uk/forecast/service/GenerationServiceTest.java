package com.example.carbon.uk.forecast.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carbon.uk.forecast.client.GenerationClient;

import lombok.RequiredArgsConstructor;

@SpringBootTest
public class GenerationServiceTest {
    @Autowired
    private GenerationClient generationClient;
    @Autowired
    private GenerationService generationService;

    @Test
    public void test_getGeneration() {
        assertTrue( !generationService.getGenerationRange(OffsetDateTime.now(), OffsetDateTime.now().plus(3, ChronoUnit.DAYS)).isEmpty());
    }

    @Test void test_optimalGeneration() {
        assertTrue(!generationService.get);
    }
}