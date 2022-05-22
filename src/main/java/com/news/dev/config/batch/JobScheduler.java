package com.news.dev.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;



@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobScheduler {

    private final JobLauncher jobLauncher;

    @Qualifier("contentsJob")
    private final Job ContentsJob;

    @Qualifier("mailJob")
    private final Job MailJob;


    @Scheduled(cron = "0 0 6 * * *") // 매일 오전 6시
    public void contentsJobBatch() {

        JobParameters jobParameters = new JobParametersBuilder()
                                                .addDate("date", new Date()).toJobParameters();

        try {
            jobLauncher.run(ContentsJob, jobParameters);
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException
                | org.springframework.batch.core.repository.JobRestartException | JobInstanceAlreadyCompleteException e) {
            log.error("error msg : " + e);
        }
    }

    @Scheduled(cron = "0 8 * * * *") // 매일 오전 8시
    public void mailJobBatch() {

        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date()).toJobParameters();

        try {
            jobLauncher.run(MailJob, jobParameters);
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException
                 | org.springframework.batch.core.repository.JobRestartException | JobInstanceAlreadyCompleteException e) {
            log.error("error msg : " + e);
        }
    }
}
