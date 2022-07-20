package com.news.dev.config.batch;

import com.news.dev.adaptor.KakaoAdaptor;
import com.news.dev.adaptor.WoowahanAdaptor;
import com.news.dev.jpa.entity.ContentsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

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

        if(!items.isEmpty()) return items;

        return null;
    }
}
