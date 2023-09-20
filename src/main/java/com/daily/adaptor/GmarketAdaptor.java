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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GmarketAdaptor implements CommonAdaptor {

    private final String url;
    private final SiteRepository siteRepository;

    public GmarketAdaptor(@Value("${daily.gmarket}") final String url,
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
            return doc.select("#cMain").select(".article_skin");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }

    @Override
    public List<Content> getNewContentsFromAdaptor(String requestDate) {
        Elements elements = this.getElements(this.getDocument());
        Site site = this.getSite(ContentType.GMARKET.name());

        return elements.parallelStream()
                .map(element -> {
                    String title = element.select(".list_content").select(".link_post .tit_post").text();
                    String link = url + element.select(".list_content").select(".link_post").attr("href");
                    String description = element.select(".list_content").select(".link_post .txt_post").text();

                    if(!"".equals(link)) {
                        try {
                            Document detailDoc = Jsoup.connect(link).get();
                            String author = detailDoc.select(".area_title").select(".txt_detail").textNodes().get(0).text().trim();
                            String[] regDates = detailDoc.select(".area_title").select(".txt_detail").textNodes().get(1).text().trim().split(". ");
                            String regDate = new StringBuilder().append(regDates[0]).append("-").append(DateType.parseMonth(regDates[1])).append("-").append(DateType.parseMonth(regDates[2])).toString();

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
                        } catch (IOException e) {
                            log.error("Exception content: {}", link);
                        }

                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Site getSite(String type) {
            return siteRepository.findById(type).orElse(null);
        }
}
