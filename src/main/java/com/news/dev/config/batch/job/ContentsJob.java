package com.news.dev.config.batch.job;

import com.news.dev.adaptor.KakaoAdaptor;
import com.news.dev.adaptor.WoowahanAdaptor;
import com.news.dev.api.contents.dto.ContentsDto;
import com.news.dev.jpa.entity.ContentsEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;


@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class ContentsJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WoowahanAdaptor woowahanAdaptor;
    private final KakaoAdaptor kakaoAdaptor;


    @Bean
    @Primary
    public Job NewContentsJob() {
        Job job = jobBuilderFactory.get("newContentsJob")
                .incrementer(new RunIdIncrementer())
                .start(NewContentsStep())
                .build();
        return job;
    }

    @Bean
    public Step NewContentsStep() {
        return stepBuilderFactory.get("newContentsStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    woowahanAdaptor.newContentsUpdate();
                    kakaoAdaptor.newContentsUpdate();

                    return RepeatStatus.FINISHED;
                })).build();
    }
}
