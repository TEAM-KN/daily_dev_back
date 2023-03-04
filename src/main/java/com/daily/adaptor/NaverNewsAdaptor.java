package com.daily.adaptor;


import com.daily.domain.contents.repository.ContentsRepository;
import com.daily.global.exception.UrlConnectionException;
import com.daily.domain.contents.dto.ContentsDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NaverNewsAdaptor {

    private Environment env;

    public NaverNewsAdaptor(Environment env) {
        this.env = env;
    }

    public Document getDocument() {
        try {
            return Jsoup.connect(Objects.requireNonNull(env.getProperty("daily.naver.it-news.url"))).get();
        } catch(Exception e) {
            throw new UrlConnectionException("요청한 URL에 접근할 수 없습니다.");
        }
    }

    public Elements getElements(Document doc) {
        try {
            return doc.select("div.newsflash_body > ul.type06_headline > li");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }

    public List<ContentsDto> setContents(Elements elements) {
        List<ContentsDto> contentsList = new ArrayList<>();

        elements.stream().map(element -> {
            String imageLink = element.select("dl > dt").get(0).select("a").attr("href");
            String link = element.select("dl > dt").get(1).select("a").attr("href");
            String title = element.select("dl > dt").get(1).select("a").text();
            String description = element.select("dl > dt").get(2).select("span .lede").text();
            String author = element.select("dl > dt").get(2).select("span .writing").text();
            String regTime = element.select("dl > dt").get(2).select("span .is_new").text();

            if(!"".equals(link)) {

            }
            return null;
        }).collect(Collectors.toList());

        return contentsList;
    }

}
