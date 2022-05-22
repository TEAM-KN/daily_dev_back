package com.news.dev.config.batch.job;

import com.news.dev.adaptor.WoowahanAdaptor;
import com.news.dev.api.contents.entity.ContentsEntity;

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

import javax.persistence.EntityManagerFactory;
import java.util.List;


@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class ContentsJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WoowahanAdaptor woowahanAdaptor;


    @Bean
    @Primary
    public Job NewContentsJob() {
        Job job = jobBuilderFactory.get("newContentsJob")
                .incrementer(new RunIdIncrementer())
                .start(NewContentsStep1())
                .on("FAILED")
                .end()
                .from(NewContentsStep1())
                .on("*")
                .to(NewContentsStep2())
                .end()
                .build();
        return job;
    }

    public Step NewContentsStep1() {
        return stepBuilderFactory.get("newContentsStep1")
                .tasklet(((stepContribution, chunkContext) -> {
                    List<ContentsEntity> newContents = woowahanAdaptor.getNewContents();

                    if(0 == newContents.size()) {
                        log.info("Contents is not Update");
                        stepContribution.setExitStatus(ExitStatus.FAILED);
                    }

                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step NewContentsStep2() {
        return stepBuilderFactory.get("newContentsStep2")
                .tasklet(((stepContribution, chunkContext) -> {
                    woowahanAdaptor.newContentsUpdate();

                    return RepeatStatus.FINISHED;
                })).build();
    }
}
