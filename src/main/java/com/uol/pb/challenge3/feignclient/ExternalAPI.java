package com.uol.pb.challenge3.feignclient;

import com.uol.pb.challenge3.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name="external-api", url="https://jsonplaceholder.typicode.com")
public interface ExternalAPI {

    List<Post> getPosts();
}
