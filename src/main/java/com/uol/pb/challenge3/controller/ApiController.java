package com.uol.pb.challenge3.controller;

import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ApiController {
    @PostMapping("/{postId}")
    ResponseEntity<String> processPost(@PathVariable(value="postId") Long postId);

    @GetMapping
    ResponseEntity<List<PostDTOResponse>> findAll();
    @DeleteMapping("/{postId}")
    ResponseEntity<Void> disable(@PathVariable(value="postId") Long postId);
    @PutMapping("/{postId}")
    ResponseEntity<Void> reprocessPost(@PathVariable(value="postId") Long postId);
}
