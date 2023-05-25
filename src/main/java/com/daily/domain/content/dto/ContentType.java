package com.daily.domain.content.dto;

import lombok.Getter;

@Getter
public enum ContentType {
    WOOWAHAN("B","WW","우아한형제들"),
    KAKAO("B", "KA", "카카오"),
    NAVER_NEWS("N", "NA", "네이버 IT 뉴스");

    private String contentType;
    private String companyCd;
    private String companyNm;

    ContentType(String contentType, String companyCd, String companyNm) {
        this.contentType = contentType;
        this.companyCd = companyCd;
        this.companyNm = companyNm;
    }
}
