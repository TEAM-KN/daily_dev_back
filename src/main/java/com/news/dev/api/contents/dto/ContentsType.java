package com.news.dev.api.contents.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ContentsType {
    WOOWAHAN("1"),
    GET_ALL("A");

    private String searchFlag;

    ContentsType(String searchFlag) {
        this.searchFlag = searchFlag;
    }
}
