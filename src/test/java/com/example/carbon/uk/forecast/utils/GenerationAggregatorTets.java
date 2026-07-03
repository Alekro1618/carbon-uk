package com.example.carbon.uk.forecast.utils;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carbon.uk.forecast.model.Generation;
import com.example.carbon.uk.forecast.model.GenerationStamp;

@SpringBootTest
public class GenerationAggregatorTets {
    @Autowired
    private GenerationAggregator aggregator;

    
    @Test
    public void test_average() {
        GenerationStamp  result = aggregator.average(createList());
        assert(result != null);
        assert(
            result.getMix().stream().map(gen -> gen.getPercentage() == 3d).allMatch(Boolean::booleanValue)
        );
    }

    @Test
    public void test_aggregating() {
        List<GenerationStamp> result = aggregator.aggregate(createList(), 1, ChronoUnit.HOURS).toList();
        
        assertEquals(result.size(), 2);
        assertEquals(result.get(0), new GenerationStamp(
            OffsetDateTime.parse("2024-01-01T00:00Z"),
            OffsetDateTime.parse("2024-01-01T01:00Z"),
            List.of(
                new Generation("biomass", 3.0),
                new Generation("gas", 3.5),
                new Generation("nuclear", 3.0)
            )
        ));
        assertEquals(result.get(1), new GenerationStamp(
            OffsetDateTime.parse("2024-01-01T01:00Z"),
            OffsetDateTime.parse("2024-01-01T02:00Z"),
            List.of(
                new Generation("biomass", 3.0),
                new Generation("gas", 2.5),
                new Generation("nuclear", 3.0)
            )
        ));
    }

    @Test
    public void test_optimal() {
        GenerationStamp result = aggregator.optimal(createList(), 1, ChronoUnit.HOURS).get();
        System.out.print(result);
        assertEquals(result, new GenerationStamp(
            OffsetDateTime.parse("2024-01-01T00:00Z"),
            OffsetDateTime.parse("2024-01-01T01:00Z"),
            List.of(
                new Generation("biomass", 3.0),
                new Generation("gas",3.5),
                new Generation("nuclear", 3.0)
            )
        ));
    }

    private List<GenerationStamp> createList() {
        List<GenerationStamp> list = new ArrayList<>();
        OffsetDateTime now = OffsetDateTime.parse("2024-01-01T00:00Z");;

        list.add(
            new GenerationStamp(
                now, 
                now.plus(30, ChronoUnit.MINUTES), 
                List.of(
                    new Generation("biomass", 4d),
                    new Generation("gas", 6d),
                    new Generation("nuclear", 3d)
                )
            )
        );

        list.add(
            new GenerationStamp(
                now.plus(30, ChronoUnit.MINUTES), 
                now.plus(60, ChronoUnit.MINUTES), 
                List.of(
                    new Generation("biomass", 2d),
                    new Generation("gas", 1d),
                    new Generation("nuclear", 3d)
                )
            )
        );

        list.add(
            new GenerationStamp(
                now.plus(60, ChronoUnit.MINUTES), 
                now.plus(90, ChronoUnit.MINUTES), 
                List.of(
                    new Generation("biomass", 3d),
                    new Generation("gas", 2d),
                    new Generation("nuclear", 3d)
                )
            )
        );

        list.add(
            new GenerationStamp(
                now.plus(90, ChronoUnit.MINUTES), 
                now.plus(120, ChronoUnit.MINUTES), 
                List.of(
                    new Generation("biomass", 3d),
                    new Generation("gas", 3d),
                    new Generation("nuclear", 3d)
                )
            )
        );

        return list;
    }

}
