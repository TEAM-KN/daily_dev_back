package com.daily.adaptor;

import com.daily.domain.content.domain.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CommonAdaptorManager {

    public List<Content> of(String requestDate, CommonAdaptor... adaptors) {
        List<Content> contents = new ArrayList<>();

        for (CommonAdaptor adaptor : adaptors) {
            if (adaptor != null) {
                contents.addAll(adaptor.getNewContentsFromAdaptor(requestDate));
            }
        }

        return contents;
    }


}
