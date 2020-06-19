package com.kucw.service;

import org.springframework.stereotype.Component;

@Component
public class MyService {

    //這個方法會被 MyAop 的 myMethodBefore 切
    public String myMethod() {
        return "success";
    }
}
