package com.daily.adaptor;

import com.daily.domain.site.repository.SiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("local")
class NaverNewsAdaptorTest {

    @Autowired
    private Environment env;
    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private NaverNewsAdaptor naverNewsAdaptor;

    @BeforeEach
    void setUp() {
        this.naverNewsAdaptor = new NaverNewsAdaptor(env, siteRepository);
    }

    @Test
    void getDocumentTest() {
    }
}