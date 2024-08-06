package com.example.rabbitmqpoc.handler;

import com.example.rabbitmqpoc.model.AlertMatch;
import com.example.rabbitmqpoc.model.EmailVerification;

public class BasicHandler {
    public void handleMessage(Object mappedObject) {
        System.out.println("Received json mapped message using SimpleMessageListenerContainer: " + mappedObject.toString());
        if (mappedObject instanceof AlertMatch) {
            System.out.println("AlertMatch: " + ((AlertMatch) mappedObject).getMatch());
        } else if(mappedObject instanceof EmailVerification){
            System.out.println("EmailVerification: " + ((EmailVerification) mappedObject).getEmail());
        } else {
            System.out.println("Unknown message type");
        }
    }
}
