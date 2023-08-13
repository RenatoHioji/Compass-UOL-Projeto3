package com.uol.pb.challenge3.jws;

import com.uol.pb.challenge3.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.JmsException;
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
        try{
            apiService.createPost(postId);
            jmsTemplate.convertAndSend("comment_post_queue", postId);
        }catch (JmsException jms) {
            throw new RuntimeException(jms);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @JmsListener(destination = COMMENT_POST_QUEUE)
    public void commentReceiverMessage(Long postId){
        try{
            apiService.findComment(apiService.findById(postId));
        }catch (JmsException jms) {
            throw new RuntimeException(jms);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @JmsListener(destination = UPDATE_POST_QUEUE)
    public void updateReceiverMessage(Long postId){
        try{
            apiService.updatingPost(apiService.findById(postId));
            jmsTemplate.convertAndSend("comment_post_queue", postId);
        }catch (JmsException jms) {
            throw new RuntimeException(jms);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
