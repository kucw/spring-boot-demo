package com.kucw.worker;

import com.kucw.Student;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkerProducer {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send(Student student) {
        rabbitmqTemplate.convertAndSend("WORKER_QUEUE", student);
        System.out.println("send message " + student + " to WORKER_QUEUE successfully");
    }
}
