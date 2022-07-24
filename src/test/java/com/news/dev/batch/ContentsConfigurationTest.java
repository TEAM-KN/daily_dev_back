package com.news.dev.batch;

import com.news.dev.TestConfiguration;
import com.news.dev.config.batch.contents.ContentsConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBatchTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class, ContentsConfiguration.class })
public class ContentsConfigurationTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private ContentsConfiguration contentsConfiguration;

    @Test
    public void test_get_contents() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "2022-07-01")
                .toJobParameters();

        // when
        jobLauncherTestUtils.setJob(contentsConfiguration.contentsJob());
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        Assertions.assertThat(jobExecution.getStepExecutions()
                .stream()
                .mapToInt(StepExecution::getWriteCount)
                .sum()
        ).isNotEqualTo(0);
    }
}
