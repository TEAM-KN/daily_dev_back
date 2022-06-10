package com.news.dev.config.batch.job;


import com.news.dev.jpa.entity.ContentsEntity;
import com.news.dev.jpa.repository.ContentsRepository;
import com.news.dev.jpa.repository.CustomContentsRepository;
import com.news.dev.api.subscriber.dto.SubscriberDto;
import com.news.dev.jpa.repository.SubscriberRepository;
import com.news.dev.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@EnableBatchProcessing
@Slf4j
@RequiredArgsConstructor
public class MailJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomContentsRepository customContentsRepository;
    private final ContentsRepository contentsRepository;
    private final SubscriberRepository subscriberRepository;
    private final MailUtil mailUtil;


    @Bean
    public Job MailSendJob() {
        Job job = jobBuilderFactory.get("mailSendJob")
//            .incrementer(new RunIdIncrementer())
//            .start(MailStep1())
//                .on("FAILED")
//                .to(FailMailStep())
//                .on("*")
//                .end()
//            .from(MailStep1())
//                .on("*")
//                .to(MailStep2())
//                .next(SuccessMailStep())
//                .on("*")
//                .end()
//            .end()
//            .build();
            .incrementer(new RunIdIncrementer())
            .start(MailStep1())
                .on("*")
                .to(MailStep2())
                .on("*")
               .end()
            .end()
            .build();

        return job;
    }

    public Step MailStep1() {
        return stepBuilderFactory.get("mailStep1")
                .tasklet(((stepContribution, chunkContext) -> {
                    List<ContentsEntity> newContents =  customContentsRepository.findNewContents();

                    if(newContents.isEmpty()) {
                        log.info("Contents is not Update");
                        stepContribution.setExitStatus(ExitStatus.FAILED);
                    } else {
                        log.info("New Contents is Updated");
                        stepContribution.setExitStatus(ExitStatus.COMPLETED);
                    }

                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step MailStep2() {
        return stepBuilderFactory.get("mailStep2")
                .tasklet(((stepContribution, chunkContext) -> {
                    List<SubscriberDto> subscribers = subscriberRepository.findSubscribers();

                    String[] address = new String[subscribers.size()];

                    // 메일 주소(구독자 ID) 추출
                    for(int i=0; i<subscribers.size(); i++) {
                        address[i] = subscribers.get(i).getUsername();
                    }

                    // New 컨텐츠
                    Map newContents = new HashMap();
                    newContents.put("contents", customContentsRepository.findNewContents());

                    // 메일 발송
                    mailUtil.sendEmail(address, newContents);

                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step FailMailStep() {
        return stepBuilderFactory.get("failMailStep")
            .tasklet(((stepContribution, chunkContext) -> {
                log.info("Fail Mail Step !");
                return RepeatStatus.FINISHED;
            })).build();
    }

    @Bean
    public Step SuccessMailStep() {
        return stepBuilderFactory.get("successMailStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    log.info("Success Mail Step !");
                    return RepeatStatus.FINISHED;
                })).build();
    }

}
