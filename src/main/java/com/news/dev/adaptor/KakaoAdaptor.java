package com.news.dev.adaptor;

import com.news.dev.api.contents.dto.ContentsDto;
import com.news.dev.api.contents.dto.ContentsType;
import com.news.dev.response.exception.UrlConnectionException;
import com.news.dev.jpa.entity.ContentsEntity;
import com.news.dev.jpa.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoAdaptor {

    private final ContentsRepository contentsRepository;

    @Value("${kakao.blog.url}")
    private String url;

    // Get Html
    public Document getDocument() {
        try {
            log.info("url : {}", url);
            return Jsoup.connect(url).get();
        } catch(Exception e) {
            throw new UrlConnectionException("요청한 URL에 접근할 수 없습니다.");
        }
    }

    // Get Element
    public Elements getElements(Document doc) {
        try {
            return doc.select(".elementor-posts-container").select(".elementor-posts--skin-classic > article");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }

    // Set Contents
    public List<ContentsDto> getNewContents() {
        Elements elements = this.getElements(this.getDocument());
        List<ContentsDto> contentsList = new ArrayList<>();

        for(Element element : elements) {
            ContentsDto contentsDto = new ContentsDto();

            String link = element.select(".elementor-post__text > h3").select("a").attr("href");
            String title = element.select(".elementor-post__text > h3").select("a").text();
            String author = element.select(".elementor-post__meta-data").select(".elementor-post-author").text();
            String regDate = element.select(".elementor-post__meta-data").select(".elementor-post-date").text().replace(".", "-");
            String description = element.select(".elementor-post__excerpt").select("p").text();

            if(!"".equals(link) && link != null) {
                LocalDate regDtmParsing = LocalDate.parse(regDate, DateTimeFormatter.ISO_DATE);
                LocalDate nowDtm = LocalDate.now();

                if(nowDtm.minusDays(1).isEqual(regDtmParsing)) {
                    contentsDto.setLink(link);
                    contentsDto.setTitle(title);
                    contentsDto.setAuthor(author);
                    contentsDto.setRegDtm(regDate.replace(".", "-"));
                    contentsDto.setDescription(description);
                    contentsDto.setContentType(ContentsType.KAKAO.getContentType());
                    contentsDto.setCompanyCd(ContentsType.KAKAO.getCompanyCd());
                    contentsDto.setCompanyNm(ContentsType.KAKAO.getCompanyNm());

                    contentsList.add(contentsDto);
                }
            }

        }

        return contentsList;
    }

    public List<ContentsEntity> newContentsUpdate() {
        List<ContentsDto> newContents = getNewContents();

        List<ContentsEntity> rqEntity = newContents.stream().map(newContent ->
                new ModelMapper().map(newContent, ContentsEntity.class)).collect(Collectors.toList());

        List<ContentsEntity> rsEntity = contentsRepository.saveAll(rqEntity);
        return rsEntity;
    }
}
