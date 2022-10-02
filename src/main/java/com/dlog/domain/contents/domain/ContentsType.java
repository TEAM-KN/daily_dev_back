package com.dlog.domain.contents.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContentsType {
    WOOWAHAN("B","WW","우아한형제들"),
    KAKAO("B", "KA", "카카오"),
    IT_NEWS("N", "NV", "네이버");

    private String contentType;
    private String companyCd;
    private String companyNm;

    ContentsType(String contentType, String companyCd, String companyNm) {
        this.contentType = contentType;
        this.companyCd = companyCd;
        this.companyNm = companyNm;
    }
}
