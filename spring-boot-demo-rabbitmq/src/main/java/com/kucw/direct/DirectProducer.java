package com.kucw.direct;

import com.kucw.Student;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectProducer {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send(Student student) {
        rabbitmqTemplate.convertAndSend("DIRECT_QUEUE", student);
        System.out.println("send message " + student + " to DIRECT_QUEUE successfully");
    }
}
