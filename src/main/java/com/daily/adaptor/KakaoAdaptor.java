package com.daily.adaptor;

import com.daily.domain.content.domain.Content;
import com.daily.comn.exception.UrlConnectionException;
import com.daily.domain.content.dto.ContentDto;
import com.daily.domain.content.dto.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class KakaoAdaptor implements CommonAdaptor {

    private final Environment env;

    public KakaoAdaptor(Environment env) {
        this.env = env;
    }

    public Document getDocument() {
        try {
            String url = env.getProperty("daily.kakao.blog.url");
            return Jsoup.connect(url).get();
        } catch(Exception e) {
            throw new UrlConnectionException("요청한 URL에 접근할 수 없습니다.");
        }
    }

    public Elements getElements(Document doc) {
        try {
            return doc.select(".elementor-posts-container").select(".elementor-posts--skin-classic > article");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }

    @Override
    public List<Content> getNewContentsFromAdaptor(String requestDate) {
        Elements elements = this.getElements(this.getDocument());

        List<Content> contents = elements.stream()
                .map(element -> {
                    String link = element.select(".elementor-post__text > h3").select("a").attr("href");
                    String title = element.select(".elementor-post__text > h3").select("a").text();
                    String author = element.select(".elementor-post__meta-data").select(".elementor-post-author").text();
                    String regDate = element.select(".elementor-post__meta-data").select(".elementor-post-date").text().replace(".", "-");
                    String description = element.select(".elementor-post__excerpt").select("p").text();

                    if(!"".equals(link)) {
                        LocalDate regDtmParsing = LocalDate.parse(regDate, DateTimeFormatter.ISO_DATE);
                        LocalDate nowDtm = LocalDate.parse(requestDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        if(nowDtm.minusDays(1).isEqual(regDtmParsing)) {
                            ContentDto content = ContentDto.builder()
                                    .link(link)
                                    .title(title)
                                    .author(author)
                                    .regDtm(regDate.replace(".", "-"))
                                    .description(description)
                                    .contentType(ContentType.KAKAO.getContentType())
                                    .companyCd(ContentType.KAKAO.getCompanyCd())
                                    .companyNm(ContentType.KAKAO.getCompanyNm())
                                    .build();

                            return this.convertToContents(content);
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return contents;
    }

    public Content convertToContents(ContentDto content) {
        return new ModelMapper().map(content, Content.class);
    }
}
