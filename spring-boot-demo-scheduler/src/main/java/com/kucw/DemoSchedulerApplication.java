package com.kucw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //要加上這個 annotation，@Scheduled 才會生效
@SpringBootApplication
public class DemoSchedulerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSchedulerApplication.class, args);
    }
}
