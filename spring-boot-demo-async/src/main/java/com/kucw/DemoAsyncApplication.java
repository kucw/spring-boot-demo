package com.kucw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync //一定要加這個 @EnableAsync annotation，@Async 才會有作用
@SpringBootApplication
public class DemoAsyncApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoAsyncApplication.class, args);
    }
}
