package com.daily.adaptor;

import com.daily.domain.content.domain.Content;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonAdaptorManager {

    public List<Content> of(String requestDate, CommonAdaptor... adaptors) {
        List<Content> contents = new ArrayList<>();

        for (CommonAdaptor adaptor : adaptors) {
            if (adaptor != null) {
                List<Content> newContent = adaptor.getNewContentsFromAdaptor(requestDate);
                contents.addAll(newContent);
            }
        }
        return contents;
    }
}
