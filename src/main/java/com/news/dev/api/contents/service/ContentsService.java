package com.news.dev.api.contents.service;

import com.news.dev.api.contents.dto.ContentsRequest;
import com.news.dev.api.contents.dto.ContentsResponse;

import java.util.List;

public interface ContentsService {

    public List<ContentsResponse> list(ContentsRequest contentsRequest);
}
