package com.daily.adaptor;

import com.daily.domain.site.repository.SiteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.env.Environment;

class NaverNewsAdaptorTest {

    @Mock
    private Environment env;
    @Mock
    private SiteRepository siteRepository;

    private NaverNewsAdaptor naverNewsAdaptor = new NaverNewsAdaptor(env, siteRepository);

    @Test
    void getDocumentTest() {
    }
}