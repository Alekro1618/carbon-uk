package com.example.carbon.uk.forecast.controller;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import com.example.carbon.uk.forecast.model.GenerationStamp;
import com.example.carbon.uk.forecast.service.GenerationService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/generation")
@RequiredArgsConstructor
@Validated
public class GenerationController {
    private final GenerationService service;

    @GetMapping("")
    public List<GenerationStamp> getGenerationRange(
        @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
        @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to
    ) {
        //Convert to standard interval if some parameters are null
        OffsetDateTime From = (from != null) ? from : OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS);
        OffsetDateTime To = (to != null) ? to : From.plus(2, ChronoUnit.DAYS);

        return service.getGenerationRange(From, To);
    }
    

    @GetMapping("/optimal")
    public GenerationStamp getOptimalRange(
        @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
        @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to,
        @RequestParam(name = "window", defaultValue = "2") @Min(1) @Max(6) int window
    )
    {
        //Convert to standard interval if some parameters are null
        OffsetDateTime From = (from != null) ? from : OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS);
        OffsetDateTime To = (to != null) ? to : From.plus(2, ChronoUnit.DAYS);

        return service.getOptimalRange(From, To, window);
    }
    
}
