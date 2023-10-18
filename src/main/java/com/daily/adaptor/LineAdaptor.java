package com.daily.adaptor;

import com.daily.adaptor.exception.UrlConnectionException;
import com.daily.domain.content.domain.Content;
import com.daily.domain.content.dto.ContentDto;
import com.daily.domain.content.dto.ContentType;
import com.daily.domain.site.domain.Site;
import com.daily.domain.site.repository.SiteRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class LineAdaptor implements CommonAdaptor {

    private final String url;
    private final SiteRepository siteRepository;

    public LineAdaptor(@Value("${daily.line}") final String url,
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
            return doc.select(".post_wrap").select(".post_area .post_list").select(".post_list_item");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }

    @Override
    @Async
    public CompletableFuture<List<Content>> getNewContentsFromAdaptor(String requestDate) {
        Elements elements = this.getElements(this.getDocument());
        Site site = this.getSite(ContentType.LINE.name());

        final String originUrl = url.replace("/ko/blog", "");
        return CompletableFuture.completedFuture(elements.parallelStream()
                .map(element -> {
                    String title = element.select(".title").select("a").text();
                    String author = element.select(".written_by").select(".text_area .text_name").text();
                    String regDate = element.select(".written_by").select(".text_area .text_date").text();
                    String link = originUrl + element.select(".desc").select("a").attr("href");
                    String description = element.select(".desc").select(".text").text();

                    if(!"".equals(link)) {
                        LocalDate regDtmParsing = LocalDate.parse(regDate, DateTimeFormatter.ISO_DATE);
                        LocalDate nowDtm = LocalDate.parse(requestDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        if(nowDtm.minusDays(1).isEqual(regDtmParsing)) {
                            ContentDto content = ContentDto.builder()
                                    .siteCode(site.getSiteCode())
                                    .link(link)
                                    .title(title)
                                    .author(author)
                                    .regDtm(regDate)
                                    .description(description)
                                    .build();

                            return this.convertToContents(content);
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
}
