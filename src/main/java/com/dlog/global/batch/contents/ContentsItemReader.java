package com.dlog.global.batch.contents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ContentsItemReader<T> implements ItemReader {

    private final List<T> items;

    public ContentsItemReader(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        return !this.items.isEmpty() ? this.items.remove(0) : null;
    }
}
