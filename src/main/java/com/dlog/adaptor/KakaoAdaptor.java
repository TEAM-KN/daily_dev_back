package com.dlog.adaptor;

import com.dlog.domain.contents.domain.Contents;
import com.dlog.global.exception.UrlConnectionException;
import com.dlog.domain.contents.dto.ContentsDto;
import com.dlog.domain.contents.domain.ContentsType;
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
public class KakaoAdaptor {

    private final Environment env;

    KakaoAdaptor(Environment env) {
        this.env = env;
    }

    public Document getDocument() {
        try {
            String url = env.getProperty("dlog.kakao.blog.url");
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

    public List<Contents> getNewContentsFromKakao(String requestDate) {
        Elements elements = this.getElements(this.getDocument());

        List<Contents> contents = elements.stream()
                .map(element -> {
                    String link = element.select(".elementor-post__text > h3").select("a").attr("href");
                    String title = element.select(".elementor-post__text > h3").select("a").text();
                    String author = element.select(".elementor-post__meta-data").select(".elementor-post-author").text();
                    String regDate = element.select(".elementor-post__meta-data").select(".elementor-post-date").text().replace(".", "-");
                    String description = element.select(".elementor-post__excerpt").select("p").text();

                    if(!"".equals(link)) {
                        LocalDate regDtmParsing = LocalDate.parse(regDate, DateTimeFormatter.ISO_DATE);
                        LocalDate nowDtm = LocalDate.now();

                        if(nowDtm.minusDays(1).isEqual(regDtmParsing)) {
                            ContentsDto content = ContentsDto.builder()
                                    .link(link)
                                    .title(title)
                                    .author(author)
                                    .regDtm(regDate.replace(".", "-"))
                                    .description(description)
                                    .contentType(ContentsType.KAKAO.getContentType())
                                    .companyCd(ContentsType.KAKAO.getCompanyCd())
                                    .companyNm(ContentsType.KAKAO.getCompanyNm())
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

    public Contents convertToContents(ContentsDto content) {
        return new ModelMapper().map(content, Contents.class);
    }
}
