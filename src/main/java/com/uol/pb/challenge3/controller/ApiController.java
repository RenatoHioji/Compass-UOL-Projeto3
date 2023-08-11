package com.uol.pb.challenge3.controller;

import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface ApiController {

    ResponseEntity<String> processPost(@PathVariable(value="postId") Long postId);


    ResponseEntity<List<PostDTOResponse>> findAll();

    ResponseEntity<String> disable(@PathVariable(value="postId") Long postId);

    ResponseEntity<String> reprocessPost(@PathVariable(value="postId") Long postId);
}
