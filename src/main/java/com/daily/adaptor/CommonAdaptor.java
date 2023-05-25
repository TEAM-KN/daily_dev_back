package com.daily.adaptor;

import com.daily.domain.content.domain.Content;

import java.util.List;

public interface CommonAdaptor {

    List<Content> getNewContentsFromAdaptor(String requestDate);

}
