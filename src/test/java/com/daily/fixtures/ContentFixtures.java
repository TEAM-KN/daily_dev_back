package com.daily.fixtures;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.dto.ContentResponse;

import java.util.List;

public class ContentFixtures {

    public static final Long contentId = 1L;
    public static final String siteCode = "NAVER";
    public static final String title = "Naver News";
    public static final String description = "네이버 뉴스를 읽어보아요.";
    public static final String link = "TEST.TEST.COM";
    public static final String regDtm = "9999-99-99";
    public static final String author = "ADMIN";

    public static final List<ContentResponse> contentDtoList = List.of(NAVER_CONTENT_RESPONSE());

    public static final List<Content> contentEntityList = List.of(NAVER_CONTENT_ENTITY());

    public static ContentResponse NAVER_CONTENT_RESPONSE() {
        return ContentResponse.builder()
                .contentId(contentId)
                .siteCode(siteCode)
                .title(title)
                .description(description)
                .link(link)
                .regDtm(regDtm)
                .author(author)
                .build();
    }

    public static Content NAVER_CONTENT_ENTITY() {
        return Content.builder()
                .contentId(contentId)
                .siteCode(siteCode)
                .title(title)
                .description(description)
                .link(link)
                .regDtm(regDtm)
                .author(author)
                .build();
    }

}
