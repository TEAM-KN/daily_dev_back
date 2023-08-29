package com.daily.domain.content.service;

import com.daily.common.ServiceTest;
import com.daily.domain.content.application.ContentService;
import com.daily.domain.content.dto.ContentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static com.daily.fixtures.ContentFixtures.contentEntityList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ContentServiceTest extends ServiceTest {

    @InjectMocks
    private ContentService contentService;

    @DisplayName("사이트의 컨텐츠 정보를 정상적으로 조회")
    @Test
    void fetchContentsBySiteCodeTest() {
        // given & when
        final String siteCode = "NAVER";
        given(contentRepository.findAllBySiteCodeOrderByCreateDateDesc(any(String.class))).willReturn(contentEntityList);

        // then
        List<ContentResponse> contentBySiteResponse = contentEntityList.stream().map(ContentResponse::new).collect(Collectors.toList());
        assertThat(contentService.fetchContentsBySiteCode(siteCode)).usingRecursiveComparison().isEqualTo(contentBySiteResponse);

    }

}