package com.example.carbon.uk.forecast.utils;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carbon.uk.forecast.model.Generation;
import com.example.carbon.uk.forecast.model.GenerationStamp;
import com.example.carbon.uk.forecast.service.GenerationService;

@SpringBootTest
public class GenerationAggregatorTets {
    @Autowired
    private GenerationAggregator aggregator;

    @Test
    public void test_average() {
        List<GenerationStamp> list = new ArrayList<>();
        OffsetDateTime now = OffsetDateTime.now();

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

        GenerationStamp  result = aggregator.average(list);
        assert(result != null);
        assert(
            result.getMix().stream().map(gen -> gen.getPercentage() == 3d).allMatch(Boolean::booleanValue)
        );
    }
}
