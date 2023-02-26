package com.dlog.global.batch.contents;

import com.dlog.adaptor.KakaoAdaptor;
import com.dlog.adaptor.WoowahanAdaptor;
import com.dlog.domain.contents.domain.Contents;
import com.dlog.domain.contents.repository.ContentsRepository;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ContentsConfiguration {

    private final String JOB_NAME = "contentsJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final ContentsRepository contentsRepository;
    private final KakaoAdaptor kakaoAdaptor;
    private final WoowahanAdaptor woowahanAdaptor;

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
                .listener(new ContentsJobListener(contentsRepository))
                .build();
    }

    @Bean(JOB_NAME + "_contentsStep")
    @JobScope
    public Step contentsStep(@Value("#{jobParameters[requestDate]}") String requestDate) throws Exception {
        return stepBuilderFactory.get(JOB_NAME + "_contentsStep")
                .<Contents, Contents>chunk(1)
                .reader(new ContentsItemReader(getContents(requestDate)))
                .writer(this.contentsWriter())
                .build();
    }

    private JpaItemWriter<Contents> contentsWriter() throws Exception {
        JpaItemWriter<Contents> jpaItemWriter = new JpaItemWriterBuilder<Contents>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();

        jpaItemWriter.afterPropertiesSet();

        return jpaItemWriter;
    }

    private List<Contents> getContents(String requestDate) {

        List<Contents> woowahanContents = woowahanAdaptor.getNewContentsFromWoowahan(requestDate);
        List<Contents> kakaoContents = kakaoAdaptor.getNewContentsFromKakao(requestDate);

        List<Contents> contents =
                Stream.concat(woowahanContents.stream(), kakaoContents.stream()).collect(Collectors.toList());
        return contents;
    }

}
