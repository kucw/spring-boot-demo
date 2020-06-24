package com.kucw.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class MyService {

    //這個方法因為加上了 @Async annotation，所以會被另一個thread asynchronous的執行，因此call他的人不需要等到它做完
    @Async
    public void myMethodWithoutReturnValue() {

        System.out.println("myMethodWithoutReturnValue start...");

        //睡5秒，模擬做一個很久的計算
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("myMethodWithoutReturnValue finish!");
    }

    //有返回值的 @Async job，返回值要寫成 Future<XXX> 的形式
    @Async
    public Future<String> myMethodReturnString() {

        System.out.println("myMethodReturnString start...");

        //睡5秒，模擬做一個很久的計算
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("myMethodReturnString finish!");

        return new AsyncResult<>("Finish!");
    }
}
