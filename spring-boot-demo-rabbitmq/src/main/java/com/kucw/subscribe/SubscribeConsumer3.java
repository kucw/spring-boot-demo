package com.kucw.subscribe;

import com.kucw.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SubscribeConsumer3 {

    @RabbitListener(queues = "SUBSCRIBE_QUEUE_3")
    public void listen(Student student) {
        System.out.println("receive message from SUBSCRIBE_QUEUE_3: " + student);
    }
}