package com.example.demo.test.flow;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Data @Accessors(chain = true)
public class Flow<T,R> {
    private final Supplier<T> supplier;
    private final Function<T,R> function;
    private final Consumer<R> consumer;

    private int limit=0;

    public void invoke() {
        T t;
        int i=0;
        while ((t = supplier.get())!=null) {
            R r;
            if (function!=null) {
                r = function.apply(t);
            } else {
                r = (R) t;
            }
            consumer.accept(r);

            if (limit!=0 && ++i>=limit) break;
        }
    }
}
