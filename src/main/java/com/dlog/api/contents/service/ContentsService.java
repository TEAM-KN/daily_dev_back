package com.dlog.api.contents.service;

import com.dlog.api.contents.dto.ContentsResponse;

import java.util.List;

public interface ContentsService {

    List<ContentsResponse> list();
    void update();

}
