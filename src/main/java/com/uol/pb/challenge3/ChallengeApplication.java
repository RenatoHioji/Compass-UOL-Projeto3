package com.uol.pb.challenge3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJms
@EnableFeignClients
@EnableAsync
public class ChallengeApplication {
	private static final String MESSAGE_QUEUE = "message_queue";

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);

	}

}