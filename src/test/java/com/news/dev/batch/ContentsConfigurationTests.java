package com.news.dev.batch;

import com.news.dev.config.batch.contents.ContentsConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class, ContentsConfiguration.class })
public class ContentsConfigurationTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void test_get_contents() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "2022-07-01")
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
//        Assertions.assertThat(jobExecution.getStepExecutions().stream());

    }
}
