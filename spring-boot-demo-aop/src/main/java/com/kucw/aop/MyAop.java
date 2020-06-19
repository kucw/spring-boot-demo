package com.kucw.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//在這個 class 裡就可以寫 Spring aop 的語法
@Aspect
@Component
public class MyAop {

    @Before("execution(* com.kucw.service.MyService.myMethod())")
    public void myMethodBefore(){
        System.out.println("Before");
    }

}
