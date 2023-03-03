package com.daily.domain.contents.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@Builder
public class ContentsResponse {

    private Long contentId;
    private String title; // 제목
    private String description; // 내용
    private String link; // 기술블로그 링크
    private String regDtm; // 등록일시
    private String author; // 작성자
    private String contentsType; // 블로그 or 뉴스
    private String companyCd; // 회사 코드
}
