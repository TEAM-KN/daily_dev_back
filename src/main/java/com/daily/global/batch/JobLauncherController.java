package com.daily.global.batch;

import com.daily.global.batch.contents.ContentsConfiguration;
import com.daily.global.batch.mail.SendMailConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class JobLauncherController {

    private final JobLauncher jobLauncher;
    private final ContentsConfiguration contentsConfiguration;
    private final SendMailConfiguration sendMailConfiguration;

    @Scheduled(cron = "0 0 1 * * ?")
    public void launchJobToContent() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", LocalDate.now().toString())
                .toJobParameters();
        jobLauncher.run(contentsConfiguration.contentsJob(), jobParameters);
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void launchJobToMail() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", LocalDate.now().toString())
                .toJobParameters();
        jobLauncher.run(sendMailConfiguration.sendMailJob(), jobParameters);
    }
}
