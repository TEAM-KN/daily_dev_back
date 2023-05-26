package com.daily.domain.content.dto;

import com.daily.domain.content.domain.Content;
import com.daily.domain.site.domain.Site;
import lombok.Data;

@Data
public class ContentResponse {

    private Long contentId;

    private String siteCode;
    private String title;
    private String description;
    private String link;
    private String regDtm;
    private String author;
    private String contentType;

    private String companyCode;

    private String companyName;

    private Site site;

    public ContentResponse(Content content) {
        this.contentId = content.getContentId();
        this.title = content.getTitle();
        this.description = content.getDescription();
        this.link = content.getLink();
        this.regDtm = content.getRegDtm();
        this.author = content.getAuthor();
        this.contentType = content.getContentType();
        this.companyCode = content.getCompanyCode();
        this.companyName = content.getCompanyName();
    }
}
