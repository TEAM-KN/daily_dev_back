package com.daily.global.batch.contents;

import org.springframework.batch.item.ItemReader;

import java.util.ArrayList;
import java.util.List;

public class ContentsItemReader<T> implements ItemReader {

    private final List<T> items;

    public ContentsItemReader(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    public Object read() {
        return !this.items.isEmpty() ? this.items.remove(0) : null;
    }
}
