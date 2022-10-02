package com.dlog.adaptor;

import org.assertj.core.api.Assertions;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WoowahanAdaptorTest {

    @Autowired
    private WoowahanAdaptor woowahanAdaptor;

    @DisplayName("우아한 기술블로그 Document 가져오기")
    @Test
    void getDocumentTest() {
        Document doc = woowahanAdaptor.getDocument();
        String result = doc.html();

        Assertions.assertThat(result.contains("<title>우아한형제들 기술블로그</title>")).isTrue();
    }
}