package com.dlog.config.batch;

import com.dlog.jpa.entity.ContentsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class StepShareContext <T> {

    private Map<String, T> contentsData;

    public StepShareContext() {
        this.contentsData = new ConcurrentHashMap<>();
    }

    public void putContentsData(String key, List<? extends ContentsEntity> data) {
        if(contentsData == null) return;

        contentsData.put(key, (T) data);
    }

    public T getContentsData(String key) {
        if(contentsData == null) return null;

        return contentsData.get(key);
    }

}
