package com.kucw.worker;

import com.kucw.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkerConsumer2 {

    @RabbitListener(queues = "WORKER_QUEUE")
    public void listen(Student student) {
        System.out.println("WorkerConsumer2 receive message from WORKER_QUEUE: " + student);
    }
}