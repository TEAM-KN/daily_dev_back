package com.news.dev.config.batch;

import com.news.dev.jpa.entity.ContentsEntity;
import com.news.dev.jpa.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class StepShareContext <T> {

    private Map<String, T> contentsData;
    private Map<String, T> subscriberData;

    public StepShareContext() {
        this.contentsData = new ConcurrentHashMap<>();
        this.subscriberData = new ConcurrentHashMap<>();
    }

    public void putContentsData(String key, List<? extends ContentsEntity> data) {
        if(contentsData.isEmpty()) return;

        contentsData.put(key, (T) data);
    }

    public T getContentsData(String key) {
        if(contentsData.isEmpty()) return null;

        return contentsData.get(key);
    }

    public void putSubscriberData(String key, List<? extends UserEntity> data) {
        if(subscriberData.isEmpty()) return;

        subscriberData.put(key, (T) data);
    }

    public T getSubscriberData(String key) {
        if(subscriberData.isEmpty()) return null;

        return subscriberData.get(key);
    }


}
