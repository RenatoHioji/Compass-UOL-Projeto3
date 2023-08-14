package com.uol.pb.challenge3.jms;

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
    private static final String UPDATE_POST_QUEUE = "update_post_queue";
    private final ApiService apiService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = PROCESS_POST_QUEUE)
    public void postReceiverMessage(Long postId){
            apiService.createPost(postId);

    }

    @JmsListener(destination = COMMENT_POST_QUEUE)
    public void commentReceiverMessage(Long postId){
            apiService.findComment(apiService.findById(postId));

    }

    @JmsListener(destination = UPDATE_POST_QUEUE)
    public void updateReceiverMessage(Long postId){
            apiService.updatingPost(apiService.findById(postId));

    }
}
