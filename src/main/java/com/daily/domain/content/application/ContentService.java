package com.daily.domain.content.application;

import com.daily.domain.content.dto.ContentResponse;

import java.util.List;

public interface ContentService {

    List<ContentResponse> fetchContents();
    ContentResponse fetchContentsId(Long id);
    void update();

}
