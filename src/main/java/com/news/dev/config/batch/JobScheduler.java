package com.news.dev.config.batch;

import com.news.dev.config.batch.job.ContentsJob;
import com.news.dev.config.batch.job.MailJob;
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

    private final ContentsJob contentsJob;

    private final MailJob mailJob;


    @Scheduled(cron = "0 0 1 * * *") // 매일 오전 1시
    public void contentsJobBatch() {

        JobParameters jobParameters = new JobParametersBuilder()
                                                .addDate("date", new Date()).toJobParameters();

        try {
            jobLauncher.run(contentsJob.NewContentsJob(), jobParameters);
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException
                | org.springframework.batch.core.repository.JobRestartException | JobInstanceAlreadyCompleteException e) {
            log.error("error msg : " + e);
        }
    }

    @Scheduled(cron = "0 0 9 * * *") // 매일 오전 9시
    public void mailJobBatch() {

        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date()).toJobParameters();

        try {
            jobLauncher.run(mailJob.MailSendJob(), jobParameters);
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException
                 | org.springframework.batch.core.repository.JobRestartException | JobInstanceAlreadyCompleteException e) {
            log.error("error msg : " + e);
        }
    }
}
