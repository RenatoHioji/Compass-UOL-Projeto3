package com.uol.pb.challenge3.jws;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsService {
    private final JmsTemplate jmsTemplate;
    public void sendMessageToQueue(String destination, String message){
        jmsTemplate.convertAndSend(destination, message);
    }




}
