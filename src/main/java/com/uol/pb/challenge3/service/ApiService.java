package com.uol.pb.challenge3.service;

import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import com.uol.pb.challenge3.entity.Comment;
import com.uol.pb.challenge3.entity.HistoryEnum;
import com.uol.pb.challenge3.repository.CommentRepository;
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
    private final CommentRepository commentRepository;
    public Post getPost(Long postId){
        return externalAPI.getPostById(postId);
    }
    public List<Comment> getComments(Long postId){
        return externalAPI.getCommentsByPostId(postId);
    }
    public void processPost(Long postId){
        createPost(postId);
        findPost(postId);
        postOk(postId);
        findComment(postId);
        commentOk(postId);
        enabled(postId);
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
    public void postOk(Long postId){
        historyRepository.save(new History(Instant.now(), "POST_OK", repository.findById(postId).orElseThrow(()->
                new RuntimeException("Post was not found by id " + postId))));

    }
    public void findComment(Long postId){
        List<Comment> commentList = getComments(postId);
        Post post = repository.findById(postId).orElseThrow(() -> new RuntimeException("Post was not found by id " + postId));
        commentList.forEach(comment -> commentRepository.save(new Comment(comment.getBody(), post)));
        historyRepository.save(new History(Instant.now(), "COMMENTS_FIND",post));
    }
    public void commentOk(Long postId){
        historyRepository.save(new History(Instant.now(), "COMMENTS_OK", repository.findById(postId).orElseThrow(()->
                new RuntimeException("Post was not found by id" + postId))));
    }
    public void enabled(Long postId){
        historyRepository.save(new History(Instant.now(), "ENABLED", repository.findById(postId).orElseThrow(()->
                new RuntimeException("Post was not found by id " + postId))));
    }
    public void disabled(Long postId){
        repository.findById(postId).orElseThrow(() -> new RuntimeException("Post was not found by id " + postId));
        List<History> historyList = historyRepository.findAllByPostId(postId);
        if(historyList.get(historyList.size() - 1).getStatus().equals(HistoryEnum.valueOf("DISABLED"))){
            throw new RuntimeException("The post is already disabled");
        }
        historyRepository.save(new History(Instant.now(), "DISABLED", repository.findById(postId).orElseThrow(()->
                new RuntimeException("Post was not found by id " + postId))));
    }
    public List<PostDTOResponse> findAll(){
        return repository.findAll().stream().map(PostDTOResponse::new).toList();
    }
}
