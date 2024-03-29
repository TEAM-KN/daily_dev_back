package com.daily.adaptor;


import com.daily.domain.content.domain.Content;
import com.daily.domain.content.dto.ContentDto;
import com.daily.domain.content.dto.ContentType;
import com.daily.domain.site.domain.Site;
import com.daily.domain.site.repository.SiteRepository;
import com.daily.adaptor.exception.UrlConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
@Transactional
public class NaverNewsAdaptor implements CommonAdaptor {

    private Environment env;
    private SiteRepository siteRepository;

    public NaverNewsAdaptor(Environment env,
                            SiteRepository siteRepository) {
        this.env = env;
        this.siteRepository = siteRepository;
    }

    public Document getDocument(LocalDate requestDate) {
        try {
            String replaceRequestDate = requestDate.toString().replace("-", "");

            StringBuilder url = new StringBuilder(Objects.requireNonNull(env.getProperty("daily.naver.it-news.url")));
            url.append("&date=").append(replaceRequestDate);

            return Jsoup.connect(url.toString()).get();
        } catch(Exception e) {
            throw new UrlConnectionException();
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
    @Async
    public CompletableFuture<List<Content>> getNewContentsFromAdaptor(String requestDate) {
        LocalDate date = LocalDate.parse(requestDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).minusDays(1);
        Elements elements = this.getElements(this.getDocument(date));
        Site site = this.getSite();

        return CompletableFuture.completedFuture(elements.parallelStream().map(element -> {
            Map<String, String> header = new HashMap<>();
            element.select("dl > dt").forEach(dt -> {
                if ("photo".equals(dt.className())) {
                    header.put("imageLink", dt.select("a").select("img").attr("src"));
                } else {
                    header.put("link", dt.select("a").attr("href"));
                    header.put("title", dt.select("a").text());
                }
            });

            String description = element.select("dl > dd").get(0).select("span.lede").text();
            String author = element.select("dl > dd").get(0).select("span.writing").text();

            if(!"".equals(header.get("link"))) {
                ContentDto content = ContentDto.builder()
                        .siteCode(site.getSiteCode())
                        .link(header.get("link"))
                        .title(header.get("title"))
                        .author(author)
                        .regDtm(requestDate)
                        .description(description)
                        .build();
                return this.convertToContents(content);
            }
            return null;
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList()));
    }

    private Site getSite() {
        return siteRepository.findById(ContentType.NAVER.name()).orElse(null);
    }

    public Content convertToContents(ContentDto content) {
            return content.toEntity();
        }
}
