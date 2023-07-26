package com.example.demo;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class DBConsumer<T> implements Consumer<T> {
    private final JpaRepository repository;
    private final int maxBufferSize;

    private List<T> list = new ArrayList<>();

    @Override
    public void accept(T t) {
        list.add(t);
        if (list.size()>=maxBufferSize) {
            repository.saveAll(list);
            list.clear();
        }
    }
}
