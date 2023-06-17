package com.daily.domain.content.application;

import com.daily.domain.content.dto.ContentResponse;

import java.util.List;

public interface ContentService {

    List<ContentResponse> fetchContents();
    List<ContentResponse> fetchContents(String siteCode);
    ContentResponse fetchContentsId(Long id);
    void update();

}
