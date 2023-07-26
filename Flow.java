package com.example.demo;

import lombok.Data;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Data
public class Flow<T,R> {
    private final Supplier<T> supplier;
    private final Function<T,R> function;
    private final Consumer<R> consumer;

    public void invoke() {
        T t;
        while ((t = supplier.get())!=null) {
            R r = function.apply(t);
            consumer.accept(r);
        }
    }
}
