package com.news.dev.config.batch;

import com.news.dev.api.subscriber.dto.SubscriberDto;
import com.news.dev.jpa.entity.ContentsEntity;
import com.news.dev.jpa.entity.UserEntity;
import com.news.dev.jpa.repository.UserRepository;
import com.news.dev.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SendMailConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final MailUtil mailUtil;

    private final StepShareContext<ContentsEntity> shareContents;

    @Bean
    public Job sendMailJob() throws Exception {
        return jobBuilderFactory.get("sendMailJob")
                .incrementer(new RunIdIncrementer())
                .start(getContentsStep(null))
                .next(getSubscriberStep())
                .build();
    }

    @Bean
    @JobScope
    public Step getContentsStep(@Value("#{jobParameters[requestDate]}") String requestDate) throws Exception {
        return stepBuilderFactory.get("getContentsStep")
                .<ContentsEntity, ContentsEntity>chunk(10)
                .reader(contentsItemReader(requestDate))
                .writer(contentsItemWriter())
                .build();
    }

    @Bean
    public Step getSubscriberStep() throws Exception {
        return stepBuilderFactory.get("getSubscriberStep")
                .<UserEntity, UserEntity>chunk(10)
                .reader(subscriberItemReader())
                .writer(subscriberItemWriter())
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
        param.put("now", requestDate);

        JpaCursorItemReader<ContentsEntity> contentsItemReader = new JpaCursorItemReaderBuilder<ContentsEntity>()
                .name("contentsItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from ContentsEntity c where date_format(c.updDtm, '%Y-%m-%d') >= :now")
                .parameterValues(param)
                .build();

        contentsItemReader.afterPropertiesSet();

        return contentsItemReader;
    }

    private ItemWriter<? super UserEntity> subscriberItemWriter() {
        return items -> {
            log.info("subscriber size : {}", items.size());

            String[] address = new String[items.size()];
            Map<String,Object> contents = new HashMap<>();

            /* Bean으로 데이터 공유 */
            List<ContentsEntity> lists = (List<ContentsEntity>) shareContents.getContentsData("sendContents");

            contents.put("contents", lists);

            // 메일 주소(구독자 ID) 추출
            for(int i=0; i<items.size(); i++) {
                address[i] = items.get(i).getUsername();
            }

            if(items.size() < 1 && contents == null) return;

            mailUtil.sendEmail(address, contents);
        };

    }

    private ItemReader<? extends UserEntity> subscriberItemReader() throws Exception {

        JpaCursorItemReader<UserEntity> itemReaderBuilder = new JpaCursorItemReaderBuilder<UserEntity>()
                .name("getSubscriberReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select u from UserEntity u where u.subscribeYn = 'Y'")
                .build();

        itemReaderBuilder.afterPropertiesSet();

        return itemReaderBuilder;
    }

}
