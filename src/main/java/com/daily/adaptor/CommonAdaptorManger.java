package com.daily.adaptor;

import com.daily.domain.contents.domain.Contents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CommonAdaptorManger {

    public List<Contents> of(String requestDate, CommonAdaptor... adaptors) {
        List<Contents> contents = new ArrayList<>();

        for (CommonAdaptor adaptor : adaptors) {
            if (adaptor != null) {
                contents.addAll(adaptor.getNewContentsFromAdaptor(requestDate));
            }
        }

        return contents;
    }


}
