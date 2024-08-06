package com.example.rabbitmqpoc.consumer;

import com.example.rabbitmqpoc.model.AlertMatch;
import com.example.rabbitmqpoc.model.EmailVerification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.example.rabbitmqpoc.config.RabbitMqConfiguration.JSON_MAPPED_QUEUE_NAME;

@Service
public class RabbitMqConsumer {
    @RabbitListener(queues = JSON_MAPPED_QUEUE_NAME, containerFactory = "rabbitMqListenerContainerFactory")
    public void listenForJsonMappedMessages(Object mappedObject) {
        System.out.println("Received json mapped message using container factory: " + mappedObject.toString());
        if (mappedObject instanceof AlertMatch) {
            System.out.println("AlertMatch: " + ((AlertMatch) mappedObject).getMatch());
        } else if(mappedObject instanceof EmailVerification){
            System.out.println("EmailVerification: " + ((EmailVerification) mappedObject).getEmail());
        } else {
            System.out.println("Unknown message type");
        }
    }
}
