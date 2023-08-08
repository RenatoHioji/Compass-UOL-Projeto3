package com.uol.pb.challenge3.controller;


import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import com.uol.pb.challenge3.entity.Post;
import com.uol.pb.challenge3.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class ApiController {
    private final ApiService apiService;

    @PostMapping("/{postId}")
    public void processPost(@PathVariable(value="postId") Long postId){
        apiService.processPost(postId);
    }

    @GetMapping
    public ResponseEntity<List<PostDTOResponse>> findAll(){
        return ResponseEntity.ok(apiService.findAll());
    }
}
