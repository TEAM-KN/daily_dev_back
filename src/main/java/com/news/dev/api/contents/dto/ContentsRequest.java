package com.news.dev.api.contents.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentsRequest {
    // 조회할 기술블로그
    private ContentsType contentsType;
}
