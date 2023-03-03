package com.daily.global.batch.mail;

import com.daily.domain.comn.util.MailUtil;
import com.daily.domain.user.domain.User;
import com.daily.global.batch.StepShareContext;
import com.daily.domain.contents.domain.Contents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SendMailConfiguration {

    private final String JOB_NAME = "sendMailJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final MailUtil mailUtil;
    private final StepShareContext<Contents> shareContents;

    @Bean(JOB_NAME)
    public Job sendMailJob() throws Exception {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(getContentsStep(null))
                .next(sendMailStep())
                .listener(new SendMailJobListener())
                .build();
    }

    @Bean(JOB_NAME + "_getContentsStep")
    @JobScope
    public Step getContentsStep(@Value("#{jobParameters[requestDate]}") String requestDate) throws Exception {
        return stepBuilderFactory.get(JOB_NAME + "_getContentsStep")
                .<Contents, Contents>chunk(10)
                .reader(contentsItemReader(requestDate))
                .writer(contentsItemWriter())
                .build();
    }

    @Bean(JOB_NAME + "_sendMailStep")
    public Step sendMailStep() throws Exception {
        return stepBuilderFactory.get(JOB_NAME + "_sendMailStep")
                .<User, User>chunk(10)
                .reader(this.sendMailItemReader())
                .writer(this.sendMailItemWriter())
                .build();
    }

    private ItemWriter<? super Contents> contentsItemWriter() {
        return items -> {
            log.info("new contents size : {}", items.size());
            shareContents.putContentsData("sendContents", items);
        };
    }

    private ItemReader<? extends Contents> contentsItemReader(String requestDate) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("now", LocalDateTime.parse(requestDate + "T00:00:00"));

        JpaCursorItemReader<Contents> contentsItemReader = new JpaCursorItemReaderBuilder<Contents>()
                .name(JOB_NAME + "_contentsItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Contents c where c.createDate >= :now")
                .parameterValues(param)
                .build();

        contentsItemReader.afterPropertiesSet();

        return contentsItemReader;
    }

    private ItemReader<? extends User> sendMailItemReader() throws Exception {
        JpaCursorItemReader<User> itemReaderBuilder = new JpaCursorItemReaderBuilder<User>()
                .name(JOB_NAME + "_sendMailItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select u from User u where u.subscribeYn = 'Y'")
                .build();

        itemReaderBuilder.afterPropertiesSet();

        return itemReaderBuilder;
    }

    private ItemWriter<? super User> sendMailItemWriter() {
        return items -> {
            String[] recipients = items.stream().map(User::getEmail).toArray(String[]::new);
            Map<String,Object> contents = this.prepareEmailContents();

            if(items.size() >= 1 && contents != null) {
                mailUtil.sendEmail(recipients, "새로운 기술 이슈가 도착했습니다.", "mail", contents);
            } else {
                log.warn("No recipients or contents found, skipping email sending.");
            }

            shareContents.removeContentsData();
        };

    }

    public Map<String, Object> prepareEmailContents() {
        List<Contents> list = (List<Contents>) shareContents.getContentsData("sendContents");
        Map<String, Object> contents = new HashMap<>();

        contents.put("contents", list);
        return contents;
    }

}
