package com.daily.domain.content.dto;

import com.daily.domain.content.domain.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ContentDto {
    private Long contentId;
    private String siteCode;
    private String title;
    private String description;
    private String link;
    private String regDtm;
    private String author;

    public Content toEntity() {
        return Content.builder()
                .siteCode(this.getSiteCode())
                .title(this.getTitle())
                .description(this.getDescription())
                .link(this.getLink())
                .regDtm(this.getRegDtm())
                .author(this.author)
                .build();
    }
}

