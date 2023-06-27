package com.daily.adaptor;

import com.daily.domain.site.repository.SiteRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;

public class DaangnAdaptorTest {

    @Mock
    private SiteRepository siteRepository;
    private final DaangnAdaptor daangnAdaptor = new DaangnAdaptor("https://about.daangn.com/blog/", siteRepository);

    @DisplayName("문서를 정상적으로 가져오는지 확인")
    @Test
    void getDocumentTest() throws IOException {
        // given
        String url = "https://about.daangn.com/blog/";
        Document testDoc = Jsoup.parse("<html><head><title>당근마켓 팀 블로그</title></head><body></body></html>");

        // when
        Document result = Jsoup.connect(url).get();

        // then
        Assertions.assertEquals(testDoc.title(), result.title());
    }
}
