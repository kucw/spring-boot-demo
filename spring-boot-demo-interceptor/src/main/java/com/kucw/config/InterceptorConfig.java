package com.kucw.config;

import com.kucw.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//SpringBoot配置攔截器
@Configuration
@Import(MyInterceptor.class) //把攔截器的bean import進來
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private HandlerInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //指定哪些uri要被myInterceptor給攔截
        registry.addInterceptor(myInterceptor).addPathPatterns("/api/**");
    }
}
