package com.example.carbon.uk.forecast.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.example.carbon.uk.forecast.model.Generation;
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
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to) {

        OffsetDateTime From = (from != null) ? from : OffsetDateTime.now();
        OffsetDateTime To = (to != null) ? to : From.plus(3, ChronoUnit.DAYS);

        return service.getGenerationRange(From, To);
    }
    

    @GetMapping("/optim")
    public String getOptimalRange(
        @RequestParam @Min(1) @Max(6) int window)
    {
        //TODO:
        return "";
    }
    
}
