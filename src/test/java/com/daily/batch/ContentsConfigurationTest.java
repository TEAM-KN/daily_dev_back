package com.daily.batch;

import com.daily.TestConfiguration;
import com.daily.adaptor.KakaoAdaptor;
import com.daily.adaptor.NaverNewsAdaptor;
import com.daily.adaptor.WoowahanAdaptor;
import com.daily.domain.contents.repository.ContentsRepository;
import com.daily.global.batch.contents.ContentsConfiguration;
import com.daily.global.batch.contents.ContentsJobListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@Import({ ContentsJobListener.class })
@ContextConfiguration(classes = { ContentsConfiguration.class, TestConfiguration.class})
@DataJpaTest
@ActiveProfiles("local")
public class ContentsConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private KakaoAdaptor kakaoAdaptor;

    @Autowired
    private WoowahanAdaptor woowahanAdaptor;

    @Autowired
    private NaverNewsAdaptor naverNewsAdaptor;

    @Autowired
    private ContentsRepository contentsRepository;

    @Test
    public void testContents() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "2023-03-14")
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution);
        Assertions.assertEquals(3, contentsRepository.findAll().size());
    }
}
