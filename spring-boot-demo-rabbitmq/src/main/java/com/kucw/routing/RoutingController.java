package com.kucw.routing;

import com.kucw.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoutingController {

    @Autowired
    private RoutingProducer routingProducer;

    @RequestMapping("/routing/send")
    public String send() {
        routingProducer.send(new Student(1, "John"));
        routingProducer.send(new Student(2, "Amy"));
        routingProducer.send(new Student(3, "Bob"));
        return "success";
    }
}
