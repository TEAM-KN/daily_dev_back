package com.news.dev.config.batch.job;


import com.news.dev.adaptor.WoowahanAdaptor;
import com.news.dev.api.contents.entity.ContentsEntity;
import com.news.dev.api.contents.repository.ContentsRepository;
import com.news.dev.api.contents.repository.CustomContentsRepository;
import com.news.dev.api.subscriber.dto.SubscriberDto;
import com.news.dev.api.subscriber.repository.SubscriberRepository;
import com.news.dev.auth.user.entity.UserEntity;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
@Slf4j
@RequiredArgsConstructor
public class MailJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomContentsRepository contentsRepository;
    private final SubscriberRepository subscriberRepository;
    private final MailUtil mailUtil;


    @Bean
    public Job mailJob() {
        Job job = jobBuilderFactory.get("mailJob")
                .incrementer(new RunIdIncrementer())
                .start(mailStep1())
                .on("FAILED")
                .end()
                .from(mailStep1())
                .on("*")
                .to(mailStep2())
                .end()
                .build();
        return job;
    }

    public Step mailStep1() {
        return stepBuilderFactory.get("mailStep1")
                .tasklet(((stepContribution, chunkContext) -> {
                    List<ContentsEntity> newContents =  contentsRepository.findNewContents();

                    if(0 == newContents.size()) {
                        log.info("Contents is not Update");
                        stepContribution.setExitStatus(ExitStatus.FAILED);
                    }

                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step mailStep2() {
        return stepBuilderFactory.get("mailStep2")
                .tasklet(((stepContribution, chunkContext) -> {
                    List<SubscriberDto> subscribers = subscriberRepository.findSubscribers();

                    String[] emails = new String[subscribers.size()];

                    // 메일 주소(구독자 ID) 추출
                    for(int i=0; i<subscribers.size(); i++) {
                        emails[i] = subscribers.get(i).getUsername();
                    }

                    // 메일 발송
                    mailUtil.sendEmail(emails);


                    return RepeatStatus.FINISHED;
                })).build();
    }

}
