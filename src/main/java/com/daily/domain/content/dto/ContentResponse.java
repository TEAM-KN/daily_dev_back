package com.daily.domain.content.dto;

import com.daily.domain.content.domain.Content;
import lombok.Data;

@Data
public class ContentResponse {

    private Long contentId;

    private String siteCode;
    private String siteName;
    private String title;
    private String description;
    private String link;
    private String regDtm;
    private String author;

    public ContentResponse(Content content) {
        this.contentId = content.getContentId();
        this.title = content.getTitle();
        this.description = content.getDescription();
        this.link = content.getLink();
        this.regDtm = content.getRegDtm();
        this.author = content.getAuthor();
        this.siteCode = content.getSiteCode();
        this.siteName = content.getSite().getSiteName();
    }
}
