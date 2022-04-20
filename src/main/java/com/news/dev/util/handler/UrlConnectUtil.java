package com.news.dev.util.handler;

import com.news.dev.exception.UrlConnectionException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class UrlConnectUtil {

    public String getHtml(String url) {
        try {
            return Jsoup.connect(url).get().html();
        } catch (Exception e) {
            throw new UrlConnectionException("URL에 접근 할 수 없습니다.");
        }

    }

}
