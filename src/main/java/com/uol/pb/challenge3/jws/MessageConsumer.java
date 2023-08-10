package com.uol.pb.challenge3.jws;

import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import com.uol.pb.challenge3.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private static final String PROCESS_POST_QUEUE = "process_post_queue";
    private static final String COMMENT_POST_QUEUE = "comment_post_queue";
    private final ApiService apiService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = PROCESS_POST_QUEUE)
    public void postReceiverMessage(Long postId){
        apiService.createPost(postId);
        jmsTemplate.convertAndSend("comment_post_queue", apiService.findById(postId));
    }

    @JmsListener(destination = COMMENT_POST_QUEUE)
    public void commentReceiverMessage(PostDTOResponse postDTOResponse){
        apiService.findComment(postDTOResponse);
    }
}