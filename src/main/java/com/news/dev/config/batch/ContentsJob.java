package com.news.dev.config.batch;

import com.news.dev.adaptor.WoowahanAdaptor;
import com.news.dev.api.contents.dto.ContentsDto;
import com.news.dev.api.contents.entity.ContentsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ContentsJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final WoowahanAdaptor woowahanAdaptor;

    @Bean
    public Job newContentsJob() {
        Job job = jobBuilderFactory.get("newContentsJob")
                .start(newContentsStep())
                .build();
        return job;
    }

    @Bean
    public Step newContentsStep() {
        return stepBuilderFactory.get("newContentsStep")
                .<List<ContentsEntity>, List<ContentsEntity>>chunk(10)
                .processor((Function<? super List<ContentsEntity>, ? extends List<ContentsEntity>>) processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<List<ContentsDto>, List<ContentsEntity>> processor() {
        return new ItemProcessor<List<ContentsDto>, List<ContentsEntity>>() {
            @Override
            public List<ContentsEntity> process(List<ContentsDto> contents) throws Exception {
                List<ContentsEntity> rsEntity = new ArrayList<>();

                return rsEntity;
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<List<ContentsEntity>> writer() {
        return new JpaItemWriterBuilder<List<ContentsEntity>>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
