package com.uol.pb.challenge3.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface ApiInterface {
    @PostMapping("/posts/{postId}")
    String getPost(@PathVariable(value="postId") Long postId);
}
