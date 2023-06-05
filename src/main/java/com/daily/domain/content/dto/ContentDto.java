package com.daily.domain.content.dto;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ContentDto {
    private Long contentNo;
    private String title;
    private String description;
    private String link;
    private String regDtm;
    private String author;
}
