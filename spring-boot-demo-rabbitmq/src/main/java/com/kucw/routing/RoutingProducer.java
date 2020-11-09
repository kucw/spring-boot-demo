package com.kucw.routing;

import com.kucw.Student;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoutingProducer {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send(Student student) {
        String routingKey = student.getName();
        rabbitmqTemplate.convertAndSend("ROUTING_EXCHANGE", routingKey, student);
        System.out.println("send message " + student + " to ROUTING_EXCHANGE successfully");
    }
}
