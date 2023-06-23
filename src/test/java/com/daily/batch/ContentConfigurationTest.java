//package com.daily.batch;
//
//import com.daily.global.batch.contents.ContentsConfiguration;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.batch.core.*;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//@SpringBatchTest
//@SpringBootTest(classes = { ContentsConfiguration.class, TestConfiguration.class })
//@ActiveProfiles("local")
//public class ContentConfigurationTest {
//
//    @Autowired
//    private JobLauncherTestUtils jobLauncherTestUtils;
//
////    private ContentRepository contentRepository;
//    @Test
//    void testContents() throws Exception {
//        System.out.println("test");
//        // given
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("requestDate", "2023-06-14")
//                .toJobParameters();
//
//        // when
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
//
//        // then
//        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
////        Assertions.assertEquals(3, contentRepository.findAll().size());
//    }
//}
