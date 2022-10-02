package com.dlog.config.batch;


import com.dlog.config.batch.contents.ContentsConfiguration;
import com.dlog.config.batch.mail.SendMailConfiguration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobLauncher;
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
    private final ContentsConfiguration contentsConfiguration;
    private final SendMailConfiguration sendMailConfiguration;

    @Scheduled(cron = "0 0 1 * * *") // 매일 오전 1시
    public void contentsJobBatch() {

        JobParameters jobParameters = new JobParametersBuilder()
                                                .addDate("date", new Date()).toJobParameters();

        try {
            jobLauncher.run(contentsConfiguration.contentsJob(), jobParameters);
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException
                | org.springframework.batch.core.repository.JobRestartException | JobInstanceAlreadyCompleteException e) {
            log.error("error msg : " + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 9 * * *") // 매일 오전 9시
    public void mailJobBatch() {

        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date()).toJobParameters();

        try {
            jobLauncher.run(sendMailConfiguration.sendMailJob(), jobParameters);
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException
                 | org.springframework.batch.core.repository.JobRestartException | JobInstanceAlreadyCompleteException e) {
            log.error("error msg : " + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
