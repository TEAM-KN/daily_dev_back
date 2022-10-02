package com.dlog.config.batch.mail;

import com.dlog.config.batch.StepShareContext;
import com.dlog.jpa.entity.UserEntity;
import com.dlog.util.MailUtil;
import com.dlog.jpa.entity.ContentsEntity;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private final StepShareContext<ContentsEntity> shareContents;

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
                .<ContentsEntity, ContentsEntity>chunk(10)
                .reader(contentsItemReader(requestDate))
                .writer(contentsItemWriter())
                .build();
    }

    @Bean(JOB_NAME + "_sendMailStep")
    public Step sendMailStep() throws Exception {
        return stepBuilderFactory.get(JOB_NAME + "_sendMailStep")
                .<UserEntity, UserEntity>chunk(10)
                .reader(this.sendMailItemReader())
                .writer(this.sendMailItemWriter())
                .build();
    }

    private ItemWriter<? super ContentsEntity> contentsItemWriter() {
        return items -> {
            log.info("new contents size : {}", items.size());
            shareContents.putContentsData("sendContents", items);
        };
    }

    private ItemReader<? extends ContentsEntity> contentsItemReader(String requestDate) throws Exception {
        Map<String, Object> param = new HashMap<>();
//        param.put("now", requestDate);
        param.put("now", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        JpaCursorItemReader<ContentsEntity> contentsItemReader = new JpaCursorItemReaderBuilder<ContentsEntity>()
                .name(JOB_NAME + "_contentsItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from ContentsEntity c where date_format(c.updDtm, '%Y-%m-%d') >= :now")
                .parameterValues(param)
                .build();

        contentsItemReader.afterPropertiesSet();

        return contentsItemReader;
    }

    private ItemWriter<? super UserEntity> sendMailItemWriter() {
        return items -> {
            log.info("subscriber size : {}", items.size());

            String[] address = new String[items.size()];
            Map<String,Object> contents = new HashMap<>();

            /* Bean으로 데이터 공유 */
            List<ContentsEntity> list = (List<ContentsEntity>) shareContents.getContentsData("sendContents");

            contents.put("contents", list);

            // 메일 주소(구독자 ID) 추출
            for(int i=0; i<items.size(); i++) {
                address[i] = items.get(i).getUsername();
            }

            if(items.size() >= 1 && list != null) {
                mailUtil.sendEmail(address, contents);
            }
        };

    }

    private ItemReader<? extends UserEntity> sendMailItemReader() throws Exception {

        JpaCursorItemReader<UserEntity> itemReaderBuilder = new JpaCursorItemReaderBuilder<UserEntity>()
                .name(JOB_NAME + "_sendMailItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select u from UserEntity u where u.subscribeYn = 'Y'")
                .build();

        itemReaderBuilder.afterPropertiesSet();

        return itemReaderBuilder;
    }

}
