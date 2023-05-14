package com.daily.adaptor;

import com.daily.domain.contents.dto.ContentsType;
import com.daily.comn.dto.DateType;
import com.daily.domain.contents.dto.ContentsDto;
import com.daily.domain.contents.domain.Contents;
import com.daily.comn.exception.UrlConnectionException;
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
public class WoowahanAdaptor implements CommonAdaptor {

    private final Environment env;

    public WoowahanAdaptor(Environment env) {
        this.env = env;
    }

    public Document getDocument() {
        try {
            String url = env.getProperty("daily.naver.it-news.url");
            return Jsoup.connect(Objects.requireNonNull(url)).get();
        } catch(Exception e) {
            throw new UrlConnectionException("요청한 URL에 접근할 수 없습니다.");
        }
    }

    public Elements getElement(Document doc) {
        try {
            return doc.select("div.posts > .item");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }

    @Override
    public List<Contents> getNewContentsFromAdaptor(String requestDate) {
        Elements elements = this.getElement(this.getDocument());

        List<Contents> contents = elements.stream()
                .map(element -> {
                    String link = element.select("a").attr("href");
                    String title = element.select("a > h1").text();
                    String description = element.select("a > h1").next().text();

                    Elements authorElement = element.children().select(".author > span");
                    String regDtm = authorElement.get(0).text();
                    String author = authorElement.get(1).text();

                    if (!"".equals(link)) {
                        String[] dateComponents = regDtm.split("[.]");

                        if (dateComponents != null) {
                            regDtm = dateComponents[2] + "-" + DateType.valueOf(dateComponents[0]).getMonth() + "-" + dateComponents[1];
                            LocalDate regDtmParsing = LocalDate.parse(regDtm, DateTimeFormatter.ISO_DATE);
                            LocalDate nowDtm = LocalDate.parse(requestDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                            if (nowDtm.minusDays(1).isEqual(regDtmParsing)) {
                                ContentsDto content = ContentsDto.builder()
                                        .link(link)
                                        .title(title)
                                        .author(author)
                                        .regDtm(regDtm)
                                        .description(description)
                                        .contentType(ContentsType.WOOWAHAN.getContentType())
                                        .companyCd(ContentsType.WOOWAHAN.getCompanyCd())
                                        .companyNm(ContentsType.WOOWAHAN.getCompanyNm())
                                        .build();

                                return this.convertToContents(content);
                            }
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



