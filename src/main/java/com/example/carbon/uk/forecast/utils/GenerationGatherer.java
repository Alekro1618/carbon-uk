package com.example.carbon.uk.forecast.utils;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

import com.example.carbon.uk.forecast.model.GenerationStamp;

/**
 * 
 * GenerationGatherer is implementation of {@link Gatherer} for {@link GenerationStamp}. 
 * Gathers stamps with given time difference. 
 * Throws {@link IllegalArgumentException} if the time differnce is smaller then intervals length.
 */
public class GenerationGatherer implements Gatherer<GenerationStamp, GenerationGatherer.State, List<GenerationStamp>> {
    private int size;
    private ChronoUnit by;

    static class State {
        List<GenerationStamp> current = new ArrayList<>();
        OffsetDateTime start = null;
    }

    public GenerationGatherer(int size, ChronoUnit by) {
        this.size = size;
        this.by = by;
    }

    @Override
    public Supplier<State> initializer() {
        return State::new;
    }

    @Override
    public Integrator<State, GenerationStamp, List<GenerationStamp>> integrator() {
       return this::integrate;
    }

    private boolean integrate(
        State state,
        GenerationStamp element,
        Downstream<? super List<GenerationStamp>> downstream
    ) {
        if (state.start == null) {
            state.start = element.getFrom();
        }

        if (!element.getFrom().isBefore(state.start.plus(size, by))) {
            List<GenerationStamp> completed = state.current;
            boolean accepted = downstream.push(completed);

            state.current = new ArrayList<>();
            state.current.add(element);
            state.start = element.getFrom();

            return accepted;
        }

        state.current.add(element);
        
        return true;
    }

    @Override
    public BiConsumer<State, Downstream<? super List<GenerationStamp>>> finisher() {
        return (state, downstream) -> {
            if (!state.current.isEmpty()) {
                downstream.push(state.current);
            }
        };
    }
    
}
