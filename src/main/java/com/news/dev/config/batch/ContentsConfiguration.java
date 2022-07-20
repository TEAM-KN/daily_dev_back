package com.news.dev.config.batch;

import com.news.dev.adaptor.KakaoAdaptor;
import com.news.dev.adaptor.WoowahanAdaptor;
import com.news.dev.jpa.entity.ContentsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ContentsConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final WoowahanAdaptor woowahanAdaptor;
    private final KakaoAdaptor kakaoAdaptor;


    @Bean
    public Job contentsJob() throws Exception {
        return jobBuilderFactory.get("contentsJob")
                .incrementer(new RunIdIncrementer())
                .start(this.contentsStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step contentsStep(@Value("#{jobParameters[requestDate]}") String requestDate) throws Exception {
        return stepBuilderFactory.get("contentsStep")
                .<ContentsEntity, ContentsEntity>chunk(10)
                .reader(new ContentsItemReader(getContents(requestDate)))
                .writer(this.contentsWriter())
                .build();
    }

    private ItemWriter contentsWriter() throws Exception {
        JpaItemWriter<ContentsEntity> jpaItemWriter = new JpaItemWriterBuilder<ContentsEntity>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true) // 중복 체킹 x
                .build();

        jpaItemWriter.afterPropertiesSet();

        return jpaItemWriter;
    }

    private List<ContentsEntity> getContents(String requestDate) {

        List<ContentsEntity> woowahanContents = woowahanAdaptor.getNewContents(requestDate);
        List<ContentsEntity> kakaoContents = kakaoAdaptor.getNewContents(requestDate);

        List<ContentsEntity>  items = Stream.of(woowahanContents, kakaoContents)
                .flatMap(c -> c.stream())
                .collect(Collectors.toList());

        return items;
    }

}
