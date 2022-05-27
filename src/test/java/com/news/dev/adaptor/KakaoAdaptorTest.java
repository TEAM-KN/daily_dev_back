package com.news.dev.adaptor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class KakaoAdaptorTest {

    @Autowired KakaoAdaptor kakaoAdaptor;

    @Test
    void getContentsTest() {
        Document doc = kakaoAdaptor.getDocument();
        Elements elements = kakaoAdaptor.getElements(doc);

        kakaoAdaptor.setContents(elements);
    }

}