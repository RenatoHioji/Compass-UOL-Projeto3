package com.uol.pb.challenge3.jws;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private static final String MESSAGE_QUEUE = "message_queue";

    @JmsListener(destination = MESSAGE_QUEUE)
    public void receiveMessage(Message message){
        System.out.println(message);
    }
}
