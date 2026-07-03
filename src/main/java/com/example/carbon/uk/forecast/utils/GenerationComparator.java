package com.example.carbon.uk.forecast.utils;

import java.util.Comparator;
import java.util.List;

import com.example.carbon.uk.forecast.model.Generation;
import com.example.carbon.uk.forecast.model.GenerationStamp;

/**
 * 
 * GenerationComparator is implementation of {@link Comparator} for {@link GenerationStamp}. 
 * It sccores each stamp by taking sum of clear energy fuels. The highest score means the greater value.
 */
public class GenerationComparator implements Comparator<GenerationStamp> {
    private List<String> clearEnergy = List.of("biomass", "nuclear", "hydro", "wind", "solar");

    @Override
    public int compare(GenerationStamp o1, GenerationStamp o2) {
        return Double.compare(getScore(o1), getScore(o2));
    }
    
    private double getScore(GenerationStamp stamp) {
        return stamp.getMix()
            .stream()
            .filter(generation -> clearEnergy.contains(generation.getFuel()))
            .mapToDouble(Generation::getPercentage)
            .sum();
    } 
}


