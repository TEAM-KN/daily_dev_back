package com.daily.adaptor;

import com.daily.adaptor.exception.UrlConnectionException;
import com.daily.domain.content.domain.Content;
import com.daily.domain.content.dto.ContentDto;
import com.daily.domain.content.dto.ContentType;
import com.daily.domain.site.domain.Site;
import com.daily.domain.site.repository.SiteRepository;
import com.daily.global.common.dto.DateType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
@Transactional
public class WoowahanAdaptor implements CommonAdaptor {

    private final String url;
    private final SiteRepository siteRepository;

    public WoowahanAdaptor(@Value("${daily.woowahan.blog.url}") final String url,
                           final SiteRepository siteRepository) {
        this.url = url;
        this.siteRepository = siteRepository;
    }

    public Document getDocument() {
        try {
            return Jsoup.connect(Objects.requireNonNull(url)).get();
        } catch(Exception e) {
            throw new UrlConnectionException();
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
    @Async
    public CompletableFuture<List<Content>> getNewContentsFromAdaptor(String requestDate) {
        Elements elements = this.getElement(this.getDocument());
        Site site = this.getSite(ContentType.WOO.name());

        return CompletableFuture.completedFuture(elements.parallelStream()
                .map(element -> {
                    String link = element.select("a").attr("href");
                    String title = element.select("a > h1").text();
                    String description = element.select("a > h1").next().text();

                    Elements authorElement = element.children().select(".author > span");
                    String regDtm = authorElement.get(0).text();
                    String author = authorElement.get(1).text();

                    if (!"".equals(link)) {
                        String[] dateComponents = regDtm.split("[.]");

                        if (dateComponents.length > 0) {
                            regDtm = dateComponents[2] + "-" + DateType.valueOf(dateComponents[0]).getMonth() + "-" + dateComponents[1];
                            LocalDate regDtmParsing = LocalDate.parse(regDtm, DateTimeFormatter.ISO_DATE);
                            LocalDate nowDtm = LocalDate.parse(requestDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                            if (nowDtm.minusDays(1).isEqual(regDtmParsing)) {
                                ContentDto content = ContentDto.builder()
                                        .siteCode(site.getSiteCode())
                                        .link(link)
                                        .title(title)
                                        .author(author)
                                        .regDtm(regDtm)
                                        .description(description)
                                        .build();

                                return this.convertToContents(content);
                            }
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    private Site getSite(String type) {
        return siteRepository.findById(type).orElse(null);
    }

    public Content convertToContents(ContentDto content) {
        return content.toEntity();
    }
}



