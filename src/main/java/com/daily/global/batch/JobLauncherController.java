package com.daily.global.batch;

import com.daily.global.batch.contents.ContentsConfiguration;
import com.daily.global.batch.mail.SendMailConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequestMapping("/launcher")
@RestController
@RequiredArgsConstructor
public class JobLauncherController {

    private final JobLauncher jobLauncher;
    private final ContentsConfiguration contentsConfiguration;
    private final SendMailConfiguration sendMailConfiguration;

    @PostMapping("/content")
    public void launchJobToContent() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", LocalDate.now().toString())
                .toJobParameters();
        jobLauncher.run(contentsConfiguration.contentsJob(), jobParameters);
    }

    @PostMapping("/mail")
    public void launchJobToMail() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", LocalDate.now().toString())
                .toJobParameters();
        jobLauncher.run(sendMailConfiguration.sendMailJob(), jobParameters);
    }
}
