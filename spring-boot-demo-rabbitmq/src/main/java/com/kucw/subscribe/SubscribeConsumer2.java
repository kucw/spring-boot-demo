package com.kucw.subscribe;

import com.kucw.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SubscribeConsumer2 {

    @RabbitListener(queues = "SUBSCRIBE_QUEUE_2")
    public void listen(Student student) {
        System.out.println("receive message from SUBSCRIBE_QUEUE_2: " + student);
    }
}