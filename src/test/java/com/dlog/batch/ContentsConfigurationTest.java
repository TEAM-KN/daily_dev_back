package com.dlog.batch;

import com.dlog.TestConfiguration;
import com.dlog.global.batch.contents.ContentsConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringRunner;

@SpringBatchTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ContentsConfiguration.class, TestConfiguration.class })
@ActiveProfiles("test")
public class ContentsConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @DisplayName("컨텐츠 유무 확인하기")
    @Test
    public void test_contents() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "2022-08-19")
                .toJobParameters();

        // when
//        jobLauncherTestUtils.setJob();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        Assertions.assertThat(jobExecution.getStepExecutions()
                .stream()
                .mapToInt(StepExecution::getWriteCount)
                .sum()
        ).isNotEqualTo(0);
    }
}
