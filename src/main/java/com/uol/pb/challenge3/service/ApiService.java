package com.uol.pb.challenge3.service;

import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import com.uol.pb.challenge3.repository.HistoryRepository;
import com.uol.pb.challenge3.repository.PostRepository;
import com.uol.pb.challenge3.entity.History;
import com.uol.pb.challenge3.entity.Post;
import com.uol.pb.challenge3.feignclient.ExternalAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final ExternalAPI externalAPI;
    private final HistoryRepository historyRepository;
    private final PostRepository repository;
    public Post getPost(Long postId){
        return externalAPI.getPostById(postId);
    }
    public void processPost(Long postId){
        createPost(postId);
        findPost(postId);
        postOk();
        findComment();
        commentOk();
        enabled();

    }
    public void createPost(Long postId){
        Post post = repository.save(new Post(postId));
        historyRepository.save(new History(Instant.now(), "CREATED", post));
    }
    public void findPost(Long postId){
        Post updatedPost = repository.findById(postId).map(post -> new Post(
                post.getId(), getPost(postId).getTitle(), getPost(postId).getBody(), post.getComment(), post.getHistory())
        ).orElseThrow(()-> new RuntimeException(""));
        repository.save(updatedPost);
        historyRepository.save(new History(Instant.now(), "POST_FIND", updatedPost));
    }
    public void postOk(){

    }
    public void findComment(){

    }
    public void commentOk(){

    }
    public void enabled(){

    }
    public List<PostDTOResponse> findAll(){
        return repository.findAll().stream().map(PostDTOResponse::new).toList();
    }
}
