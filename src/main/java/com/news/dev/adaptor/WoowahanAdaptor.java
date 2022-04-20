package com.news.dev.adaptor;

import com.news.dev.exception.UrlConnectionException;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RequiredArgsConstructor
public class WoowahanAdaptor {
    // get Html
    public Document getDocument(String url) {
        try {
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



