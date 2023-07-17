package com.daily.fixtures;

import com.daily.domain.site.domain.Site;
import com.daily.domain.site.dto.SiteParam;

public class SiteFixtures {

    /* 우아한형제들 사이트 정보 */
    public static final String WOO_SITE_CODE = "WOO";
    public static final String WOO_SITE_NAME = "우아한형제들";
    public static final String WOO_SITE_DESC = "우아한형제들";

    /* 네이버 사이트 정보 */
    public static final String NAVER_SITE_CODE = "NAVER";
    public static final String NAVER_SITE_NAME = "네이버";
    public static final String NAVER_SITE_DESC = "네이버뉴스";

    /* 네이버 사이트 정보 */
    public static final String KAKAO_SITE_CODE = "KAKAO";
    public static final String KAKAO_SITE_NAME = "카카오";
    public static final String KAKAO_SITE_DESC = "카카오";

    public static Site WOO_SITE() {
        return Site.builder()
                .siteCode(WOO_SITE_CODE)
                .siteName(WOO_SITE_NAME)
                .siteDesc(WOO_SITE_DESC)
                .build();
    }

    public static Site NAVER_SITE() {
        return Site.builder()
                .siteCode(NAVER_SITE_CODE)
                .siteName(NAVER_SITE_NAME)
                .siteDesc(NAVER_SITE_DESC)
                .build();
    }

    public static Site KAKAO_SITE() {
        return Site.builder()
                .siteCode(KAKAO_SITE_CODE)
                .siteName(KAKAO_SITE_NAME)
                .siteDesc(KAKAO_SITE_DESC)
                .build();
    }

    public static SiteParam SITE_REQUEST() {
        return new SiteParam(WOO_SITE_CODE, WOO_SITE_NAME, WOO_SITE_DESC);
    }
}
