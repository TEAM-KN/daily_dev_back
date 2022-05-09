//package com.news.dev.config.batch.job;
//
//
//import com.news.dev.api.contents.repository.ContentsRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableBatchProcessing
//@Slf4j
//@RequiredArgsConstructor
//public class MailJob {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final ContentsRepository contentsRepository;
//
//
//    @Bean
//    public Job mailJob() {
//        Job job = jobBuilderFactory.get("mailJob")
//                .incrementer(new RunIdIncrementer())
//                .start(mailStep1())
//                .on("FAILED")
//                .end()
//                .from(mailStep1())
//                .on("*")
//                .to(mailStep2())
//                .end()
//                .build();
//        return job;
//    }
//
//    public Step mailStep1() {
//        return stepBuilderFactory.get("mailStep1")
//                .tasklet(((stepContribution, chunkContext) -> {
//
//                    return RepeatStatus.FINISHED;
//                })).build();
//    }
//
//    @Bean
//    public Step mailStep2() {
//        return stepBuilderFactory.get("mailStep2")
//                .tasklet(((stepContribution, chunkContext) -> {
//
//                    return RepeatStatus.FINISHED;
//                })).build();
//    }
//
//}
