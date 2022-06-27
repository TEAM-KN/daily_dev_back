package com.news.dev.config.batch;

import com.news.dev.jpa.entity.ContentsEntity;
import com.news.dev.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SendMailConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final MailUtil mailUtil;

    @Bean
    public Job sendMailJob() throws Exception {
        return jobBuilderFactory.get("sendMailJbo")
                .incrementer(new RunIdIncrementer())
                .start(getContentsStep())
                .build();
    }

    @Bean
    public Step sendMailStep() {
        return stepBuilderFactory.get("sendMailStep")
                .<ContentsEntity, ContentsEntity>chunk(10)
                .reader()
                .writer()
    }

    @Bean
    public Step getContentsStep() throws Exception {
        return stepBuilderFactory.get("getContentsStep")
                .<ContentsEntity, ContentsEntity>chunk(10)
                .reader(contentsItemReader())
                .writer(contentsItemWriter())
    }

    private ItemWriter<? super ContentsEntity> contentsItemWriter() {
        return items -> {
            log.info("items size : {}", items.size());
        };
    }

    private ItemReader<? extends ContentsEntity> contentsItemReader() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("now", LocalDate.now());

        JpaCursorItemReader<ContentsEntity> contentsItemReader = new JpaCursorItemReaderBuilder<ContentsEntity>()
                .name("contentsItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from ContentsEntity c where c.updDtm > :now")
                .parameterValues(param)
                .build();

        contentsItemReader.afterPropertiesSet();

        return contentsItemReader;
    }

}
