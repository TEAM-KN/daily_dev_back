package com.daily.global.batch.contents;

import com.daily.adaptor.*;
import com.daily.domain.content.domain.Content;
import com.daily.domain.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ContentsConfiguration {

    private final static String JOB_NAME = "contentsJob";
    private final static Integer CHUNK_SIZE = 100;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final ContentRepository contentRepository;
    private final KakaoAdaptor kakaoAdaptor;
    private final WoowahanAdaptor woowahanAdaptor;
    private final NaverNewsAdaptor naverNewsAdaptor;
    private final DaangnAdaptor daangnAdaptor;
    private final CommonAdaptorManager adaptorManger;

    /*
    * 배치 명 : New 컨텐츠 데이터 수집 배치 프로그램
    * 배치 실행 시간 : 매일 오전 1시
    * 배치 Param : {requestDate} - 요청 시간
    *
    * */

    @Bean(JOB_NAME)
    public Job contentsJob() throws Exception {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(this.contentsStep(null))
                .listener(new ContentsJobListener(contentRepository))
                .build();
    }

    @Bean(JOB_NAME + "_contentsStep")
    @JobScope
    public Step contentsStep(@Value("#{jobParameters[requestDate]}") String requestDate) throws Exception {
        return stepBuilderFactory.get(JOB_NAME + "_contentsStep")
                .<Content, Content>chunk(CHUNK_SIZE)
                .reader(new ContentsItemReader<>(getContents(requestDate)))
                .writer(this.contentsWriter())
                .build();
    }

    private JpaItemWriter<Content> contentsWriter() throws Exception {
        JpaItemWriter<Content> jpaItemWriter = new JpaItemWriterBuilder<Content>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();

        jpaItemWriter.afterPropertiesSet();

        return jpaItemWriter;
    }

    private List<Content> getContents(String requestDate) {
        return adaptorManger.of(requestDate, woowahanAdaptor, kakaoAdaptor, naverNewsAdaptor, daangnAdaptor);
    }

}
