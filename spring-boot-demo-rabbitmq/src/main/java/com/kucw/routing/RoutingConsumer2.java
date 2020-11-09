package com.kucw.routing;

import com.kucw.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RoutingConsumer2 {

    @RabbitListener(queues = "ROUTING_QUEUE_2")
    public void listen(Student student) {
        System.out.println("receive message from ROUTING_QUEUE_2: " + student);
    }
}