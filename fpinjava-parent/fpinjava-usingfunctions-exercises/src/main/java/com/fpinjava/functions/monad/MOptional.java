package com.fpinjava.functions.monad;

import java.util.function.Function;

public class MOptional<T> {

    private final T value;

    public MOptional(T value) {
        this.value = value;
    }

    <R> MOptional<R> map(Function<T, R> transform){
        if(value != null){
            return new MOptional<>(transform.apply(value));
        }
        return new MOptional<>(null);

    }

    T OrElse(T defaultValue){
        return value != null ? value : defaultValue;
    }
}
