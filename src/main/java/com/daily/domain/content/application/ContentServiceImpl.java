package com.daily.domain.content.application;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.domain.ContentPK;
import com.daily.domain.content.dto.ContentResponse;
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
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ContentResponse> fetchContents() {
        List<Content> contentList = contentRepository.findAll();

        return contentList.stream().map(ContentResponse::new).collect(Collectors.toList());
    }

    @Override
    public ContentResponse fetchContentsId(final Long contentId) {
        ContentPK contentPK = ContentPK.builder().contentId(contentId).build();
        Optional<Content> contentsOptional = contentRepository.findById(contentPK);

        if (contentsOptional.isPresent()) {
            Content content = contentsOptional.get();
            return new ContentResponse(content);
        }
        return null;
    }

    @Override
    public void update() {}
}
