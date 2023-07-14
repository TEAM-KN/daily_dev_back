package com.daily.adaptor;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.dto.ContentDto;
import com.daily.domain.content.dto.ContentType;
import com.daily.domain.site.domain.Site;
import com.daily.domain.site.repository.SiteRepository;
import com.daily.adaptor.exception.UrlConnectionException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DaangnAdaptor implements CommonAdaptor {


    private final String url;
    private final SiteRepository siteRepository;

    public DaangnAdaptor(@Value("${daily.daangn.blog.url}") final String url,
                         final SiteRepository siteRepository) {
        this.url = url;
        this.siteRepository = siteRepository;
    }

    public Document getDocument() {
        try {
            return Jsoup.connect(url).get();
        } catch(Exception e) {
            throw new UrlConnectionException();
        }
    }

    public Elements getElements(Document doc) {
        try {
            return doc.select(".c-cRxWmX").select(".c-kBsdRb .c-gbnrwH");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }

    @Override
    public List<Content> getNewContentsFromAdaptor(String requestDate) {
        Elements elements = this.getElements(this.getDocument());
        Site site = this.getSite(ContentType.DAANGN.name());

        String originUrl = url.replace("/blog", "");
        List<Content> contents = elements.parallelStream()
                .map(element -> {
                    String link = element.select("a.c-hqmuMq").attr("href");
                    String imgUrl = element.select(".c-hqmuMq").select("img").attr("src");
                    String title = element.select(".c-hqmuMq").select("h3").text();
                    String description = element.select(".c-hqmuMq").select("p").text();
                    String author = element.select(".c-beTeij").text();

                    String detailUrl = originUrl.replace("/blog", "") + link;
                    if (!"".equals(link)) {
                        try {
                            Document detailDoc = Jsoup.connect(detailUrl).get();
                            String header = detailDoc.select(".c-yyRm").select(".c-hzPNMB").select(".c-fBvbZg").text();
                            String regDate = header.substring(header.indexOf("|") + 1).trim();

                            LocalDate regDtmParsing = LocalDate.parse(regDate, DateTimeFormatter.ISO_DATE);
                            LocalDate nowDtm = LocalDate.parse(requestDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                            if (nowDtm.minusDays(1).isEqual(regDtmParsing)) {
                                ContentDto content = ContentDto.builder()
                                        .siteCode(site.getSiteCode())
                                        .link(originUrl + link)
                                        .title(title)
                                        .author(author)
                                        .regDtm(regDate.replace(".", "-"))
                                        .description(description)
                                        .build();
                                return this.convertToContents(content);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return contents;
    }

    private Site getSite(String type) {
        return siteRepository.findById(type).orElse(null);
    }
}
