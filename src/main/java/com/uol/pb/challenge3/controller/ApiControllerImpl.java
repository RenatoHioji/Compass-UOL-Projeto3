package com.uol.pb.challenge3.controller;
import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import com.uol.pb.challenge3.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class ApiControllerImpl implements ApiController {
    private final ApiService apiService;
    private final JmsTemplate jmsTemplate;

    @Override
    @PostMapping("/{postId}")
    public ResponseEntity<String> processPost(@PathVariable(value="postId") Long postId){
        jmsTemplate.convertAndSend("process_post_queue", postId);

        return ResponseEntity.ok("CREATED");
    }
    @Override
    @GetMapping
    public ResponseEntity<List<PostDTOResponse>> findAll(){
        return ResponseEntity.ok(apiService.findAll());
    }
    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> disable(@PathVariable(value="postId") Long postId){
        apiService.disabled(apiService.findById(postId));
        return ResponseEntity.ok("Disabled");
    }
    @Override
    @PutMapping("/{postId}")
    public ResponseEntity<String> reprocessPost(@PathVariable(value="postId") Long postId){
        jmsTemplate.convertAndSend("update_post_queue", postId);
        return ResponseEntity.ok("Updated");
    }
}
