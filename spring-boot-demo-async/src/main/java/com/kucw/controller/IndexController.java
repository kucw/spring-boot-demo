package com.kucw.controller;

import com.kucw.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class IndexController {

    @Autowired
    private MyService myService;

    @RequestMapping("test1")
    public String test1(){
        myService.myMethodWithoutReturnValue();
        return "test1 success";
    }

    @RequestMapping("test2")
    public String test2(){
        Future<String> resultFuture = myService.myMethodReturnString();

        try {
            String result = resultFuture.get(); //取得 myMethodReturnString 方法的返回值
            System.out.println("result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "test2 success";
    }
}
