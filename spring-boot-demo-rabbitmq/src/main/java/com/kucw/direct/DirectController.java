package com.kucw.direct;

import com.kucw.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectController {

    @Autowired
    private DirectProducer directProducer;

    @RequestMapping("/direct/send")
    public String send() {
        directProducer.send(new Student(1, "John"));
        return "success";
    }
}
