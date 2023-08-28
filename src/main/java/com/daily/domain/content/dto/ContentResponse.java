package com.daily.domain.content.dto;

import com.daily.domain.content.domain.Content;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class ContentResponse {

    private Long contentId;
    private String siteCode;
    private String title;
    private String description;
    private String link;
    private String regDtm;
    private String author;

    public ContentResponse(Long contentId, String siteCode, String title, String description, String link, String regDtm, String author) {
        this.contentId = contentId;
        this.siteCode = siteCode;
        this.title = title;
        this.description = description;
        this.link = link;
        this.regDtm = regDtm;
        this.author = author;
    }

    public ContentResponse(Content content) {
        this.contentId = content.getContentId();
        this.title = content.getTitle();
        this.description = content.getDescription();
        this.link = content.getLink();
        this.regDtm = content.getRegDtm();
        this.author = content.getAuthor();
        this.siteCode = content.getSiteCode();
    }
}
