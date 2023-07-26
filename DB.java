package com.example.demo;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Data
public class DB<T> implements Consumer<T>, Supplier<T> {
    private final JpaRepository repository;
    private final int maxBufferSize;

    private List<T> list = new ArrayList<>();

    private Page page=null;
    private int currentPage=-1;
    private Iterator<T> iterator=null;

    @Override
    public void accept(T t) {
        list.add(t);
        if (list.size()>=maxBufferSize) {
            repository.saveAll(list);
            list.clear();
        }
    }

    @Override
    public T get() {
        if (iterator==null || !iterator.hasNext()) {
            page = repository.findAll(PageRequest.of(++currentPage, maxBufferSize));
            iterator=page.iterator();
        }

        if (!iterator.hasNext()) return null;
        return iterator.next();
    }
}
