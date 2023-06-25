package com.daily.adaptor;

import com.daily.domain.content.domain.Content;
import com.daily.domain.site.repository.SiteRepository;
import com.daily.global.exception.UrlConnectionException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DaangnAdaptor implements CommonAdaptor {

    private final Environment env;
    private final SiteRepository siteRepository;

    public DaangnAdaptor(Environment env,
                         SiteRepository siteRepository) {
        this.env = env;
        this.siteRepository = siteRepository;
    }

    public Document getDocument() {
        try {
//            String url = env.getProperty("daily.daangn.blog.url");
            return Jsoup.connect("https://about.daangn.com/blog/").get();
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

        elements.parallelStream()
                .forEach(element -> {
                    String detailUrl = element.select(".c-gmxfka > a").attr("herf");
                    String imgUrl = element.select(".c-gmxfka > a").select("img").attr("src");
                    String title = element.select(".c-gmxfka > a").select("h3").text();
                    String description = element.select(".c-gmxfka > a").select("p").text();
                    String author = element.select(".c-beTeij").text();

                    System.out.println("test");
                });


        return null;
    }
}
