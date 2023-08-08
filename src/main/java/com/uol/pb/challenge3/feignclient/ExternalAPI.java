package com.uol.pb.challenge3.feignclient;

import com.uol.pb.challenge3.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="external-api", url="https://jsonplaceholder.typicode.com")
public interface ExternalAPI {
    @GetMapping("/posts")
    List<Post> getPosts();
    @GetMapping("/posts/{postId}")
    Post getPostById(@PathVariable Long postId);
}
