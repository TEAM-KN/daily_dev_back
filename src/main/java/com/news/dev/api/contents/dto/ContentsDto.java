package com.news.dev.api.contents.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContentsDto {
    private Long contentNo;
    private String title;
    private String description;
    private String link;
    private String regDtm;
    private String author;

}
