package com.news.dev.adaptor;

import com.news.dev.api.contents.dto.ContentsDto;
import com.news.dev.exception.UrlConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class WoowahanAdaptor {

//    private final ContentsRepository contentsRepository;

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
    
    // Get Contents
    public List<ContentsDto> setContents(Elements elements) {
        List<ContentsDto> contentsList = new ArrayList<>();
        
        for(Element element : elements) {
            ContentsDto contentsDto = new ContentsDto();

            String link = element.select("a").attr("href");
            String title = element.select("a > h1").text();
            String description = element.select("a > h1").next().text();

            Elements authorElement = element.children().select(".author > span");
            String regDate = authorElement.get(0).text();
            String author = authorElement.get(1).text();

            if(!"".equals(link) && link != null) {
                contentsDto.setLink(link);
                contentsDto.setTitle(title);
                contentsDto.setDescription(description);
                contentsDto.setRegDtm(regDate);
                contentsDto.setAuthor(author);

                contentsList.add(contentsDto);
            }
        }
        
        return contentsList;
    }
    
    // Get Contents
    public List<ContentsDto> getContents() {
        Document doc = getDocument();
        Elements elements = getElement(doc);
        List<ContentsDto> contents = setContents(elements);
        
        return contents;
    }
    
    // New Contents Checking
    public boolean isNewContents() {
        return true;
    }

    // Save Contents (contents update is true)
    public void saveContents() {

    }
}



