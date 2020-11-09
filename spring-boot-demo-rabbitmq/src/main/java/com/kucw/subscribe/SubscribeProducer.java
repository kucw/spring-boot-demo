package com.kucw.subscribe;

import com.kucw.Student;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscribeProducer {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send(Student student) {
        rabbitmqTemplate.convertAndSend("SUBSCRIBE_EXCHANGE", "", student);
        System.out.println("send message " + student + " to SUBSCRIBE_EXCHANGE successfully");
    }
}
