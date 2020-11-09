package com.kucw.worker;

import com.kucw.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkerController {

    @Autowired
    private WorkerProducer workerProducer;

    @RequestMapping("/worker/send")
    public String send() {
        workerProducer.send(new Student(1, "John"));
        workerProducer.send(new Student(2, "Amy"));
        workerProducer.send(new Student(3, "Bob"));
        workerProducer.send(new Student(4, "Mike"));
        workerProducer.send(new Student(5, "Sharon"));
        return "send 5 messages to queue successfully";
    }
}
