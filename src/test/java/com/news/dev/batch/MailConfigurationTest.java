package com.news.dev.batch;

import com.news.dev.TestConfiguration;
import com.news.dev.config.batch.contents.ContentsConfiguration;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBatchTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ContentsConfiguration.class, TestConfiguration.class })
@ActiveProfiles("test")
public class MailConfigurationTest {
}
