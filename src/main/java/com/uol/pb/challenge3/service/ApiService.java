package com.uol.pb.challenge3.service;

import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import com.uol.pb.challenge3.entity.Comment;
import com.uol.pb.challenge3.entity.Post;
import com.uol.pb.challenge3.entity.enums.HistoryEnum;
import com.uol.pb.challenge3.jws.MessageConsumer;
import com.uol.pb.challenge3.repository.CommentRepository;
import com.uol.pb.challenge3.repository.HistoryRepository;
import com.uol.pb.challenge3.repository.PostRepository;
import com.uol.pb.challenge3.entity.History;
import com.uol.pb.challenge3.feignclient.ExternalAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService{
    private final ExternalAPI externalAPI;
    private final HistoryRepository historyRepository;
    private final PostRepository repository;
    private final CommentRepository commentRepository;
    private JmsTemplate jmsTemplate;

    public Optional<Post> getPost(Long postId){
        return externalAPI.getPostById(postId);
    }
    public Optional<List<Comment>> getComments(Long postId){
        return externalAPI.getCommentsByPostId(postId);
    }
    public void createPost(Long postId){
        PostDTOResponse postDTOResponse = new PostDTOResponse(repository.save(new Post(postId)));
        historyRepository.save(new History(HistoryEnum.CREATED, postDTOResponse.id()));
        log.info("CREATED");
        findPost(postDTOResponse);

    }
    public void findPost(PostDTOResponse myPostDTOResponse){
        log.info("FIND_POST");
        getPost(myPostDTOResponse.id()).ifPresentOrElse(post -> {
            PostDTOResponse updatedPostDTOResponse = new PostDTOResponse(repository.save(new Post(post.getId(), post.getTitle(), post.getBody(), myPostDTOResponse.comment(), post.getHistories())));
            historyRepository.save(new History(HistoryEnum.POST_FIND, myPostDTOResponse.id()));
            postOk(updatedPostDTOResponse);
        }, () -> failedPost(myPostDTOResponse));
    }

    public void postOk(PostDTOResponse postDTOResponse){
        log.info("POST_OK");
        historyRepository.save(new History(HistoryEnum.POST_OK, postDTOResponse.id()));
    }
    public void findComment(PostDTOResponse postDTOResponse) {
        log.info("FIND_COMMENTS");
        getComments(postDTOResponse.id()).ifPresentOrElse(comments -> {
                comments.forEach(comment -> commentRepository.save(new Comment(comment.getBody(), postDTOResponse.id())));
                historyRepository.save(new History(HistoryEnum.POST_FIND, postDTOResponse.id()));
                commentOk(postDTOResponse);
        }, () -> failedPost(postDTOResponse));
    }
    public void commentOk(PostDTOResponse postDTOResponse){
        log.info("COMMENTS_OK");
        historyRepository.save(new History(HistoryEnum.COMMENTS_OK, postDTOResponse.id()));
        enabled(postDTOResponse);
    }

    public void enabled(PostDTOResponse postDTOResponse){
        log.info("ENABLED");
        historyRepository.save(new History(HistoryEnum.ENABLED, postDTOResponse.id()));
    }

    public void disabled(PostDTOResponse postDTOResponse){
        log.info("DISABLED");
        List<History> historyList = historyRepository.findAllByPostId(new Post(postDTOResponse).getId());
        if(historyList.get(historyList.size() - 1).getStatus().equals(HistoryEnum.valueOf("DISABLED"))){
            throw new RuntimeException("The post is already disabled");
        }
        historyRepository.save(new History(HistoryEnum.DISABLED, postDTOResponse.id()));
    }
    public void updatingPost(Long postId) {
        log.info("UPDATING");
        historyRepository.save(new History(HistoryEnum.UPDATING, postId));
        findPost(findById(postId));
    }

    private void failedPost(PostDTOResponse postDTOResponse) {
        log.info("FAILED");
        historyRepository.save(new History(HistoryEnum.FAILED, postDTOResponse.id()));
        disabled(postDTOResponse);
    }
    public List<PostDTOResponse> findAll(){
         return repository.findAll().stream().map(PostDTOResponse::new).toList();
    }

    public PostDTOResponse findById(Long postId) {
        return new PostDTOResponse(repository.findById(postId).orElseThrow(() -> new RuntimeException("")));
    }
}
