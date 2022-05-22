package com.news.dev.api.contents.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContentsType {
    WOOWAHAN("B","WW","우아한형제들"),
    IT_NEWS("N", "NV", "NAVER");

    private String contentType;
    private String companyCd;
    private String companyNm;

    ContentsType(String contentType, String companyCd, String companyNm) {
        this.contentType = contentType;
        this.companyCd = companyCd;
        this.companyNm = companyNm;
    }
}
