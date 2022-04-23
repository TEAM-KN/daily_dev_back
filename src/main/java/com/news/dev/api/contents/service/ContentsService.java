package com.news.dev.api.contents.service;

import com.news.dev.api.contents.dto.ContentsRequest;
import com.news.dev.api.contents.dto.ContentsResponse;

public interface ContentsService {

    public ContentsResponse list(ContentsRequest contentsRequest);
}
