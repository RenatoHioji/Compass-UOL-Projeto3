package com.uol.pb.challenge3;

import com.uol.pb.challenge3.jws.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJms
@EnableFeignClients
@EnableAsync
public class ChallengeApplication {
	private static final String MESSAGE_QUEUE = "message_queue";

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ChallengeApplication.class, args);
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		Message message = new Message(1, "Olá");
		jmsTemplate.convertAndSend(MESSAGE_QUEUE, message);

	}

}