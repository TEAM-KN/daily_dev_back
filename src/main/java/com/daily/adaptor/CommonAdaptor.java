package com.daily.adaptor;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.dto.ContentDto;
import com.daily.domain.site.repository.SiteRepository;

import java.util.List;

public interface CommonAdaptor {

    List<Content> getNewContentsFromAdaptor(String requestDate);

    default Content convertToContents(ContentDto contentDto) {
        return contentDto.toEntity();
    }
}
