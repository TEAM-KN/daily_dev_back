package com.dlog.domain.contents.dto;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ContentsDto {
    private Long contentNo;
    private String title;
    private String description;
    private String link;
    private String regDtm;
    private String author;
    private String contentType;
    private String companyCd;
    private String companyNm;
}
