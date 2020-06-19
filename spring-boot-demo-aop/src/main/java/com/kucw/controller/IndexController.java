package com.kucw.controller;

import com.kucw.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private MyService myService;

    @RequestMapping("")
    public String index(){
        return myService.myMethod();
    }
}
