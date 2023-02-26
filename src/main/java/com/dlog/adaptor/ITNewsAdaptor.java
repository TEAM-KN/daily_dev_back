package com.dlog.adaptor;


import com.dlog.global.exception.UrlConnectionException;
import com.dlog.domain.contents.dto.ContentsDto;
import com.dlog.domain.contents.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ITNewsAdaptor {

    private final ContentsRepository contentsRepository;

    @Value("${dlog.naver.it-news.url}")
    private String url;

    // Get Html
    public Document getDocument() {
        try {
            log.info("url : {}", url);
            return Jsoup.connect(url).get();
        } catch(Exception e) {
            throw new UrlConnectionException("요청한 URL에 접근할 수 없습니다.");
        }
    }

    // Get Element
    public Elements getElements(Document doc) {
        try {
            return doc.select("div.newsflash > ul > li");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }

    // Set Contents
    public List<ContentsDto> setContents(Elements elements) {
        List<ContentsDto> contentsList = new ArrayList<>();

        for(Element element : elements) {
            ContentsDto contentsDto = new ContentsDto();

            String imageLink = element.select("dl > dt").get(0).select("a").attr("href");
            String link = element.select("dl > dt").get(1).select("a").attr("href");
            String title = element.select("dl > dt").get(1).select("a").text();
            String description = element.select("dl > dt").get(2).select("span .lede").text();
            String author = element.select("dl > dt").get(2).select("span .writing").text();
            String regTime = element.select("dl > dt").get(2).select("span .is_new").text();

        }

        return contentsList;
    }

}
