package com.daily.adaptor;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.dto.ContentDto;
import com.daily.domain.content.dto.ContentType;
import com.daily.domain.site.domain.Site;
import com.daily.domain.site.repository.SiteRepository;
import com.daily.global.exception.UrlConnectionException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DaangnAdaptor implements CommonAdaptor {

    private final Environment env;
    private final SiteRepository siteRepository;

    private static final String URL = "https://about.daangn.com/blog/";

    public DaangnAdaptor(Environment env,
                         SiteRepository siteRepository) {
        this.env = env;
        this.siteRepository = siteRepository;
    }

    public Document getDocument() {
        try {
//            String url = env.getProperty("daily.daangn.blog.url");
            return Jsoup.connect(URL).get();
        } catch(Exception e) {
            throw new UrlConnectionException("요청한 URL에 접근할 수 없습니다.");
        }
    }

    public Elements getElements(Document doc) {
        try {
            return doc.select(".c-cRxWmX").select(".c-kBsdRb .c-gmxfka");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }

    @Override
    public List<Content> getNewContentsFromAdaptor(String requestDate) {
        Elements elements = this.getElements(this.getDocument());
        Site site = this.getSite(ContentType.DAANGN);

        String originUrl = URL.replace("/blog", "");
        List<Content> contents = elements.parallelStream()
                .map(element -> {
                    String link = element.select(".c-gmxfka > a.c-hqmuMq").attr("href");
                    String imgUrl = element.select(".c-gmxfka > a").select("img").attr("src");
                    String title = element.select(".c-gmxfka > a").select("h3").text();
                    String description = element.select(".c-gmxfka > a").select("p").text();
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

    private Site getSite(ContentType type) {
        return siteRepository.findById(type.name()).orElse(null);
    }
}
