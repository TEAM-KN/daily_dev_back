package com.daily.adaptor;

import com.daily.adaptor.NaverNewsAdaptor;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

class NaverNewsAdaptorTest {

    @Mock
    private Environment env;

    private NaverNewsAdaptor naverNewsAdaptor = new NaverNewsAdaptor(env);

    @Test
    void getDocumentTest() {
    }
}