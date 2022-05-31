package com.news.dev.adaptor;

import com.news.dev.api.contents.dto.ContentsDto;
import com.news.dev.api.contents.dto.ContentsType;
import com.news.dev.api.contents.dto.DateType;
import com.news.dev.jpa.entity.ContentsEntity;
import com.news.dev.jpa.repository.ContentsRepository;
import com.news.dev.exception.UrlConnectionException;
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
@Slf4j
@RequiredArgsConstructor
public class WoowahanAdaptor {

    private final ContentsRepository contentsRepository;

    @Value("${woowahan.blog.url}")
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

    // Get Elements
    public Elements getElement(Document doc) {
        try {
            return doc.select("div.posts > .item");
        } catch (Exception e) {
            throw new NullPointerException("Element is Null...");
        }
    }
    
    // Set Contents
    public List<ContentsDto> getNewContents() {
        Elements elements = this.getElement(this.getDocument());

        List<ContentsDto> contentsList = new ArrayList<>();
        
        for(Element element : elements) {
            ContentsDto contentsDto = new ContentsDto();

            String link = element.select("a").attr("href");
            String title = element.select("a > h1").text();
            String description = element.select("a > h1").next().text();

            Elements authorElement = element.children().select(".author > span");
            String regDtm = authorElement.get(0).text();
            String author = authorElement.get(1).text();

            if(!"".equals(link) && link != null) {
                String[] dates = regDtm.split("[.]");
                regDtm = dates[2] + "-" + DateType.valueOf(dates[0]).getMonth() + "-" + dates[1];

                LocalDate regDtmParsing = LocalDate.parse(regDtm, DateTimeFormatter.ISO_DATE);
                LocalDate nowDtm = LocalDate.now();

                // 하루 전 컨텐츠 추출
                if(nowDtm.minusDays(1).isEqual(regDtmParsing)) {
                    contentsDto.setLink(link);
                    contentsDto.setTitle(title);
                    contentsDto.setDescription(description);
                    contentsDto.setRegDtm(regDtm);
                    contentsDto.setAuthor(author);
                    contentsDto.setContentType(ContentsType.WOOWAHAN.getContentType());
                    contentsDto.setCompanyCd(ContentsType.WOOWAHAN.getCompanyCd());
                    contentsDto.setCompanyNm(ContentsType.WOOWAHAN.getCompanyNm());

                    contentsList.add(contentsDto);
                }
            }
        }
        
        return contentsList;
    }

    // Init Contents
    public List<ContentsDto> initContents() {
        List<ContentsDto> contents = getNewContents();

        return contents;
    }

    // New Contents Checking
//    public List<ContentsEntity> getNewContents() {
//        Document doc = getDocument();
//        Elements elements = getElement(doc);
//        List<ContentsDto> contents = setContents(elements);
//
//        List<ContentsDto> newContents = new ArrayList<>();
//        LocalDate nowDtm = LocalDate.now();
//
//        for(ContentsDto content : contents) {
//            String regDtm = content.getRegDtm();
//            LocalDate regDtmParsing = LocalDate.parse(regDtm, DateTimeFormatter.ISO_DATE);
//
//            // Batch는 0시에 수행
//            if(nowDtm.minusDays(1).isEqual(regDtmParsing)) {
//                newContents.add(content);
//            }
//        }
//
//        return contents.stream().map(content ->
//            new ModelMapper().map(content, ContentsEntity.class)
//        ).collect(Collectors.toList());
//
//
//    }

    // New Contents Update (Batch)
    public List<ContentsEntity> newContentsUpdate() {
        List<ContentsDto> newContents = this.getNewContents();

        List<ContentsEntity> rqEntity = newContents.stream().map(newContent ->
                new ModelMapper().map(newContent, ContentsEntity.class)).collect(Collectors.toList());

        if(!rqEntity.isEmpty()) {
            return contentsRepository.saveAll(rqEntity);
        }
        return rqEntity;
    }
}



