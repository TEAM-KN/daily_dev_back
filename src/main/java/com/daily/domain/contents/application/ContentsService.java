package com.daily.domain.contents.application;

import com.daily.domain.contents.dto.ContentsResponse;

import java.util.List;

public interface ContentsService {

    List<ContentsResponse> fetchContents();
    ContentsResponse fetchContentsId(Long id);
    void update();

}
