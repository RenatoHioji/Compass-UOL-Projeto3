package com.uol.pb.challenge3.service;

import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import com.uol.pb.challenge3.entity.Comment;
import com.uol.pb.challenge3.entity.Post;
import com.uol.pb.challenge3.entity.enums.HistoryEnum;
import com.uol.pb.challenge3.repository.CommentRepository;
import com.uol.pb.challenge3.repository.HistoryRepository;
import com.uol.pb.challenge3.repository.PostRepository;
import com.uol.pb.challenge3.entity.History;
import com.uol.pb.challenge3.feignclient.ExternalAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {
    private final ExternalAPI externalAPI;
    private final HistoryRepository historyRepository;
    private final PostRepository repository;
    private final CommentRepository commentRepository;

    public Optional<Post> getPost(Long postId){
        return externalAPI.getPostById(postId);
    }
    public Optional<List<Comment>> getComments(Long postId){
        return externalAPI.getCommentsByPostId(postId);
    }
    public void createPost(Long postId){
        PostDTOResponse postDTOResponse = new PostDTOResponse(repository.save(new Post(postId)));
        historyRepository.save(new History(Instant.now(), "CREATED", new Post(postDTOResponse)));
        findPost(postDTOResponse);
        log.info("CREATED");
    }
    public void findPost(PostDTOResponse myPostDTOResponse){
        log.info("FIND_POST");
        getPost(myPostDTOResponse.id()).ifPresentOrElse(post -> {
            PostDTOResponse updatedPostDTOResponse = new PostDTOResponse(repository.save(new Post(post.getId(), post.getTitle(), post.getBody(), post.getComment(), post.getHistory())));
            historyRepository.save(new History(Instant.now(), "POST_FIND", new Post(updatedPostDTOResponse)));
            postOk(updatedPostDTOResponse);
        }, () -> failedPost(myPostDTOResponse));
    }

    public void postOk(PostDTOResponse postDTOResponse){
        log.info("POST_OK");
        historyRepository.save(new History(Instant.now(), "POST_OK", new Post(postDTOResponse)));

    }
    public void findComment(PostDTOResponse postDTOResponse) {
        log.info("FIND_COMMENTS");
        getComments(postDTOResponse.id()).ifPresentOrElse(comments -> {
            comments.forEach(comment -> commentRepository.save(new Comment(comment.getBody(), new Post(postDTOResponse))));
            historyRepository.save(new History(Instant.now(), "COMMENTS_FIND", new Post(postDTOResponse)));
            commentOk(postDTOResponse);
        }, () -> failedPost(postDTOResponse));
        }
    public void commentOk(PostDTOResponse postDTOResponse){
        log.info("COMMENTS_OK");
        historyRepository.save(new History(Instant.now(), "COMMENTS_OK", new Post(postDTOResponse)));
        enabled(postDTOResponse);
    }

    public void enabled(PostDTOResponse postDTOResponse){
        log.info("ENABLED");
        historyRepository.save(new History(Instant.now(), "ENABLED", new Post(postDTOResponse)));
    }

    public void disabled(PostDTOResponse postDTOResponse){
        List<History> historyList = historyRepository.findAllByPostId(new Post(postDTOResponse).getId());
        if(historyList.get(historyList.size() - 1).getStatus().equals(HistoryEnum.valueOf("DISABLED"))){
            throw new RuntimeException("The post is already disabled");
        }
        historyRepository.save(new History(Instant.now(), "DISABLED", new Post(postDTOResponse)));
    }
    private void updatingPost(Long postId) {
        historyRepository.save(new History(Instant.now(), "UPDATING", repository.findById(postId).orElseThrow(()->
                new RuntimeException("Post was not found by id " + postId))));
    }

    private void failedPost(PostDTOResponse postDTOResponse) {
        historyRepository.save(new History(Instant.now(), "FAILED", new Post(postDTOResponse)));
        disabled(postDTOResponse);
    }
    public List<PostDTOResponse> findAll(){
         return repository.findAll().stream().map(PostDTOResponse::new).toList();
    }

    public PostDTOResponse findById(Long postId) {
        return new PostDTOResponse(repository.findById(postId).orElseThrow(() -> new RuntimeException("")));
    }
}
