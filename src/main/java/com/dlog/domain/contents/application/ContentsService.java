package com.dlog.domain.contents.application;

import com.dlog.domain.contents.dto.ContentsResponse;

import java.util.List;

public interface ContentsService {

    List<ContentsResponse> list();
    void update();

}
