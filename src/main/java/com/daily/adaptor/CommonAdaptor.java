package com.daily.adaptor;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.dto.ContentDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CommonAdaptor {

    CompletableFuture<List<Content>> getNewContentsFromAdaptor(String requestDate);

    default Content convertToContents(ContentDto contentDto) {
        return contentDto.toEntity();
    }
}
