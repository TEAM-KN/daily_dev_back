package com.daily.domain.content.application;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.domain.ContentPK;
import com.daily.domain.content.dto.ContentResponse;
import com.daily.domain.content.exception.EmptyContentException;
import com.daily.domain.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional(readOnly = true)
    public List<ContentResponse> fetchContents() {
        List<Content> contentList = contentRepository.findAllByOrderByRegDtmDesc();

        return contentList.stream().map(ContentResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ContentResponse> fetchContentsBySiteCode(final String siteCode) {
        List<Content> contents = contentRepository.findAllBySiteCodeOrderByCreateDateDesc(siteCode);
        return contents.stream().map(ContentResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContentResponse fetchContentId(final Long contentId) {
        ContentPK contentPK = ContentPK.builder().contentId(contentId).build();
        Optional<Content> contentsOptional = contentRepository.findById(contentPK);

        if (contentsOptional.isPresent()) {
            Content content = contentsOptional.get();
            return new ContentResponse(content);
        }

        throw new EmptyContentException();
    }

}
