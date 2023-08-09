package com.uol.pb.challenge3.feignclient;

import com.uol.pb.challenge3.entity.Comment;
import com.uol.pb.challenge3.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name="external-api", url="https://jsonplaceholder.typicode.com")
public interface ExternalAPI {
    @GetMapping("/posts")
    List<Post> getPosts();
    @GetMapping("/posts/{postId}")
    Optional<Post> getPostById(@PathVariable Long postId);

    @GetMapping("/comments?postId={postId}")
    Optional<List<Comment>> getCommentsByPostId(@PathVariable Long postId);
}
