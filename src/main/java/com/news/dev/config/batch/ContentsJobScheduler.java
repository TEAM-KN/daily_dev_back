package com.news.dev.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ContentsJobScheduler {

    private final JobLauncher jobLauncher;
    private final ContentsJob contentsJob;
}
