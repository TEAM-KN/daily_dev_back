package com.daily.contents.application;

import com.daily.contents.dto.ContentsResponse;

import java.util.List;

public interface ContentsService {

    List<ContentsResponse> fetchContents();
    ContentsResponse fetchContentsId(Long id);
    void update();

}
