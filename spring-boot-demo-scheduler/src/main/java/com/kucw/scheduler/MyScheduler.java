package com.kucw.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {

    @Scheduled(fixedRate = 5000) //每5秒執行一次test1()方法
    public void test1() {
        System.out.println("test1 : 每5秒執行一次");
    }

    @Scheduled(fixedDelay = 10000) //每次執行一次test2()方法後，間隔10秒再執行下一次
    public void test2() {
        System.out.println("test2 : 10秒後執行下一次");
    }

    @Scheduled(cron = "0 0 5 * * *") //每天早上5點執行一次test3()方法
    public void test3() {
        System.out.println("test3 : 每天早上5點執行一次");
    }

    @Scheduled(cron = "${my.daily}") //也可以把cron的設定值寫在application.properties裡面，再載入進來
    public void test4() {
        System.out.println("test4 : 每10分鐘執行一次");
    }
}

