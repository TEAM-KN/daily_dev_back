package com.daily.adaptor;


import com.daily.contents.domain.Contents;
import com.daily.contents.dto.ContentsType;
import com.daily.comn.exception.UrlConnectionException;
import com.daily.contents.dto.ContentsDto;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class NaverNewsAdaptor implements CommonAdaptor {

    private Environment env;

    public NaverNewsAdaptor(Environment env) {
        this.env = env;
    }

    public Document getDocument(LocalDate requestDate) {
        try {
            String replaceRequestDate = requestDate.toString().replace("-", "");

            StringBuilder url = new StringBuilder(Objects.requireNonNull(env.getProperty("daily.naver.it-news.url")));
            url.append("&date=").append(replaceRequestDate);

            return Jsoup.connect(url.toString()).get();
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

    @Override
    public List<Contents> getNewContentsFromAdaptor(String requestDate) {
        LocalDate date = LocalDate.parse(requestDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).minusDays(1);
        Elements elements = this.getElements(this.getDocument(date));

        return elements.stream().map(element -> {

            Map<String, String> header = new HashMap<>();
            element.select("dl > dt").forEach(dt -> {
                if ("photo".equals(dt.className())) {
                    header.put("imageLink", dt.select("a").select("img").attr("src"));
                } else {
                    header.put("link", dt.select("a").attr("href"));
                    header.put("title", dt.select("a").text());
                }
            });

//            String imageLink = element.select("dl > dt.photo").get(0).select("a").select("img").attr("src");
//            String link = element.select("dl > dt").get(1).select("a").attr("href");
//            String title = element.select("dl > dt").get(1).select("a").text();
            String description = element.select("dl > dd").get(0).select("span.lede").text();
            String author = element.select("dl > dd").get(0).select("span.writing").text();

            if(!"".equals(header.get("link"))) {
                ContentsDto content = ContentsDto.builder()
                        .link(header.get("link"))
                        .title(header.get("title"))
                        .author(author)
                        .regDtm(requestDate)
                        .description(description)
                        .contentType(ContentsType.NAVER_NEWS.getContentType())
                        .companyCd(ContentsType.NAVER_NEWS.getCompanyCd())
                        .companyNm(ContentsType.NAVER_NEWS.getCompanyNm())
                        .build();
                return this.convertToContents(content);
            }
            return null;
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    }

    public Contents convertToContents(ContentsDto content) {
            return new ModelMapper().map(content, Contents.class);
        }
}
