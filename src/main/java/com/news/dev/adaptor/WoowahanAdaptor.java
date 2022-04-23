package com.news.dev.adaptor;

import com.news.dev.exception.UrlConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WoowahanAdaptor {

    @Value("${woowahan.blog.url}")
    private String url;

    // get Html
    public Document getDocument() {
        try {
            log.info("url : {}", url);
            return Jsoup.connect(url).get();
        } catch(Exception e) {
            throw new UrlConnectionException("요청한 URL에 접근할 수 없습니다.");
        }
    }

    // Element 분리
    public String getElement(Document doc) {
        try {
            Elements elements = doc.select("item firstpaint");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}



