package com.example.carbon.uk.forecast.utils;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.example.carbon.uk.forecast.model.Generation;
import com.example.carbon.uk.forecast.model.GenerationStamp;

@Component
public class GenerationAggregator {
    public Stream<GenerationStamp> aggregate(List<GenerationStamp> list, int size, ChronoUnit by) {
        return list
            .stream()
            .gather(new GenerationGatherer(size, by))
            .map(this::average);
    }

    public Optional<GenerationStamp> optimal(List<GenerationStamp> list, int window, ChronoUnit by) {
        return aggregate(list, 1, by) //aggregates stamps by 1 unit
            .gather(Gatherers.windowSliding(window)) 
            .map(this::average) //create sliding window of given size in units
            .max(new GenerationComparator());
    }

    public GenerationStamp average(List<GenerationStamp> list) {
        if (list.size() == 0) {
            throw new IllegalArgumentException("Cannot average through empty list");
        }

        OffsetDateTime start = list.getFirst().getFrom();
        OffsetDateTime end = list.getLast().getTo();

        Map<String, Double> mapAverages = list
            .stream()
            .map(GenerationStamp::getMix)
            .flatMap(List::stream)
            .collect(
                Collectors.groupingBy(
                    Generation::getFuel,
                    Collectors.averagingDouble(Generation::getPercentage)
                )
            );
        
        List<Generation> mix = mapAverages
            .entrySet()
            .stream()
            .map(entry -> new Generation(entry.getKey(), (double) entry.getValue()))
            .toList();

        return new GenerationStamp(start, end, mix);
    }
}
