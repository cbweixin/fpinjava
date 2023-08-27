package com.fpinjava.functions.monad;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class MStream<T> {
    private final List<T> values;

    public MStream(List<T> values) {
        this.values = values;
    }

    public <R> MStream<R> flatMap(Function<T, MStream<R>> transform) {
        List<R> results = new ArrayList<>();
        for( T value : values) {
            MStream<R> transformed = transform.apply(value);
            for (R tvalue : transformed.values) {
                results.add(tvalue);
            }
        }

        return new MStream<>(results);
    }

    public <R> MStream<R> map(Function<T, R> transform) {
        return this.flatMap(value -> new MStream<>(asList(transform.apply(value))));
    }

    public MStream<T> filter(Predicate<T> predicate) {
        return this.flatMap(value -> {
            if (predicate.test(value)) {
                return new MStream<>(asList(value));
            } else {
                return new MStream<>(Collections.emptyList());
            }
        });
    }

    public List<T> toList() {
        return new ArrayList<>(values);
    }
}
