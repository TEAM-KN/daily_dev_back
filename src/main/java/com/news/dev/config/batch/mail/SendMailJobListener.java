package com.news.dev.config.batch.mail;

import com.news.dev.jpa.entity.ContentsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;


@Slf4j
public class SendMailJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Job 수행시간
        long time = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();

        log.info("New 컨텐츠 메일 발송 배치 프로그램");
        log.info("--------------------------------");
        log.info("처리 시간 {}millis", time);

    }
}
