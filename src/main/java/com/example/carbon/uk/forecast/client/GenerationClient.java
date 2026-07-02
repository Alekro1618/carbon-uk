package com.example.carbon.uk.forecast.client;

import java.io.Console;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.carbon.uk.forecast.dto.GenerationResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenerationClient {
    private final RestClient generationRestClient;

    public GenerationResponse getGenerationRange(OffsetDateTime from, OffsetDateTime to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'")
            .withZone(ZoneOffset.UTC);

        String path = "/generation/%s/%s".formatted(formatter.format(from), formatter.format(to));

        return generationRestClient.get()
            .uri(path)
            .retrieve()
            .body(new ParameterizedTypeReference<GenerationResponse>() {});
    }
}
