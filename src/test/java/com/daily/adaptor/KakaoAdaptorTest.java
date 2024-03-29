package com.daily.adaptor;

import com.daily.domain.site.repository.SiteRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("local")
class KakaoAdaptorTest {

    @Autowired
    private Environment env;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private KakaoAdaptor kakaoAdaptor;

    @BeforeEach
    void setUp() {
        this.kakaoAdaptor = new KakaoAdaptor(env, siteRepository);
    }

    @DisplayName("문서를 정상적으로 가져오는지 확인")
    @Test
    void getDocumentTest() throws IOException {
        // given
        String url = "https://tech.kakao.com/blog/";
        Document testDoc = Jsoup.parse("<html><head><title>blog – tech.kakao.com</title></head><body></body></html>");

        // when
        Document result = Jsoup.connect(url).get();

        // then
        Assertions.assertEquals(testDoc.title(), result.title());
    }

    @DisplayName("문서를 파싱해서 요소 값을 정상적으로 가져오는지 확인")
    @Test
    void getElementsTest() {
        // given
        String html = "<div class=\"elementor-posts-container\"><div class=\"elementor-posts--skin-classic\"><article></article></div></div>";
        Document document = Jsoup.parse(html);

        // when
        Elements elements = kakaoAdaptor.getElements(document);

        // then
        Assertions.assertEquals(document.select(".elementor-posts-container").select(".elementor-posts--skin-classic > article"), elements);

    }

    @DisplayName("문서를 파싱해서 요소 값을 가져오는 과정에서 예외가 발생했을 때")
    @Test
    void getElementsNpeTest() {
        // given
        String html = "<html><head><title>exception !!!! </title></head><body></body></html>";
        Document document = Jsoup.parse(html);

        // when, then
        Assertions.assertThrows(NullPointerException.class, () -> kakaoAdaptor.getElements(document));
    }
}