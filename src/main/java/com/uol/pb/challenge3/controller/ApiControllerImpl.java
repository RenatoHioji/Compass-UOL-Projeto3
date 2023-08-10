package com.uol.pb.challenge3.controller;
import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import com.uol.pb.challenge3.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class ApiControllerImpl implements ApiController {
    private final ApiService apiService;
    private final JmsTemplate jmsTemplate;


    @Override
    public ResponseEntity<String> processPost(@PathVariable(value="postId") Long postId){
        jmsTemplate.convertAndSend("process_post_queue", postId);
        return ResponseEntity.ok("CREATED");
    }
    @Override
    public ResponseEntity<List<PostDTOResponse>> findAll(){
        return ResponseEntity.ok(apiService.findAll());
    }
    @Override
    public ResponseEntity<Void> disable(@PathVariable(value="postId") Long postId){
        apiService.disabled(apiService.findById(postId));
        return ResponseEntity.ok(null);
    }
    @Override
    public ResponseEntity<Void> reprocessPost(@PathVariable(value="postId") Long postId){
        return ResponseEntity.ok(null);
    }
}
