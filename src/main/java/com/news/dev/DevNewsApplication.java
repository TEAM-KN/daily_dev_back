package com.news.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DevNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevNewsApplication.class, args);
    }

}
