package com.dlog.adaptor;

import com.daily.adaptor.WoowahanAdaptor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.io.IOException;


@ExtendWith(MockitoExtension.class)
class WoowahanAdaptorTest {

    @Mock
    private Environment env;

    private WoowahanAdaptor woowahanAdaptor = new WoowahanAdaptor(env);

    @DisplayName("문서를 정상적으로 가져오는지 확인")
    @Test
    void getDocumentTest() throws IOException {
        // given
        String url = "https://techblog.woowahan.com";
        Document testDoc = Jsoup.parse("<html><head><title>우아한형제들 기술블로그</title></head><body></body></html>");

        // when
        Document result = Jsoup.connect(url).get();

        // then
        Assertions.assertEquals(testDoc.title(), result.title());

    }

    @DisplayName("URL 정보를 찾을 수 없을 경우 예외 처리")
    @Test
    void getDocumentExceptionTest() {
        // given, when
        Mockito.when(env.getProperty("dlog.woowahan.blog.url")).thenReturn(null);

        // then
        Assertions.assertThrows(NullPointerException.class, () -> {
            woowahanAdaptor.getDocument();
        });
    }

    @DisplayName("문서의 요소를 정상적으로 가져오는지 확인")
    @Test
    void getElementTest() {
        // given
        Document mockDoc = Mockito.mock(Document.class);
        Elements mockElements = Mockito.mock(Elements.class);
        Mockito.when(mockDoc.select("div.posts > .item")).thenReturn(mockElements);

        // when
        Elements resultElements = woowahanAdaptor.getElement(mockDoc);

        // then
        Assertions.assertEquals(resultElements, mockElements);
    }

    @DisplayName("문서의 요소가 Null인 경우 예외 처리")
    @Test
    void getElementNpeTest() {
        // given
        Document mockDoc = Mockito.mock(Document.class);
        Mockito.when(mockDoc.select("div.posts > .item")).thenReturn(null);

        // then
        Assertions.assertThrows(NullPointerException.class, () -> {
            woowahanAdaptor.getElement(mockDoc);
        });
    }

}