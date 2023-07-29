package com.daily.global.batch.contents;

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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ContentsRemoveConfiguration {

    private final static String JOB_NAME = "contentsRemoveJob";

    private final ContentRepository contentRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*
     * 배치 명 : 네이버 뉴스 삭제 배치 프로그램
     * 배치 실행 시간 : 매일 오전 12시
     * 배치 Param : {requestDate} - 요청 시간
     *
     * */
    @Bean(JOB_NAME)
    public Job contentsRemoveJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(this.contentsStep())
                .listener(new ContentsJobListener())
                .build();
    }

    @Bean(JOB_NAME + "_contentsStep")
    @JobScope
    public Step contentsStep() {
        return stepBuilderFactory.get(JOB_NAME + "_contentsRemoveStep")
                .tasklet((stepContribution, chunkContext) -> {
                    List<Content> contents = contentRepository.fetchRemoveNaverContentBatchQuery(LocalDate.now().minusWeeks(1));

                    if (contents.size() > 0) {
                        contentRepository.deleteAll(contents);
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
