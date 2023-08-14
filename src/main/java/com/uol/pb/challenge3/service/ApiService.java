package com.uol.pb.challenge3.service;

import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import com.uol.pb.challenge3.entity.Comment;
import com.uol.pb.challenge3.entity.Post;
import com.uol.pb.challenge3.entity.enums.HistoryEnum;
import com.uol.pb.challenge3.exceptions.ResourceNotFoundException;
import com.uol.pb.challenge3.repository.CommentRepository;
import com.uol.pb.challenge3.repository.HistoryRepository;
import com.uol.pb.challenge3.repository.PostRepository;
import com.uol.pb.challenge3.entity.History;
import com.uol.pb.challenge3.feignclient.ExternalAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
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
    private final JmsTemplate jmsTemplate;
    public Optional<Post> getPost(Long postId){
        return externalAPI.getPostById(postId);
    }
    public Optional<List<Comment>> getComments(Long postId){
        return externalAPI.getCommentsByPostId(postId);
    }
    @Async
    public void createPost(Long postId){
        verifyPostId(postId);
        log.info("CREATED");
        PostDTOResponse postDTOResponse = new PostDTOResponse(repository.save(new Post(postId)));

        History history = historyRepository.save(new History(HistoryEnum.CREATED, postDTOResponse.id()));

        findPost(postDTOResponse);

    }
    @Async
    public void findPost(PostDTOResponse myPostDTOResponse){
        log.info("FIND_POST");
        getPost(myPostDTOResponse.id()).ifPresentOrElse(post -> {
            Post posted = repository.findById(myPostDTOResponse.id()).map(postUpdate-> new Post(post.getId(), post.getTitle(), post.getTitle(), postUpdate.getComments(), postUpdate.getHistories())).orElseThrow(
                    () -> new ResourceNotFoundException("Error")
            );
            PostDTOResponse updatedPostDTOResponse = new PostDTOResponse(repository.save(posted));

            historyRepository.save(new History(HistoryEnum.POST_FIND, myPostDTOResponse.id()));
            postOk(updatedPostDTOResponse);
        }, () -> failedPost(myPostDTOResponse));
    }

    @Async
    public void postOk(PostDTOResponse postDTOResponse){
        log.info("POST_OK");
        historyRepository.save(new History(HistoryEnum.POST_OK, postDTOResponse.id()));
        jmsTemplate.convertAndSend("comment_post_queue", postDTOResponse.id());
    }
    @Async
    public void findComment(PostDTOResponse postDTOResponse) {
        log.info("FIND_COMMENTS");
        getComments(postDTOResponse.id()).ifPresentOrElse(comments -> {
                comments.forEach(comment ->
                        commentRepository.save(new Comment(comment.getBody(), postDTOResponse.id(), comment.getId())));
                historyRepository.save(new History(HistoryEnum.COMMENTS_FIND, postDTOResponse.id()));
                commentOk(postDTOResponse);
        }, () -> failedPost(postDTOResponse));
    }
    @Async
    public void commentOk(PostDTOResponse postDTOResponse){
        log.info("COMMENTS_OK");
        historyRepository.save(new History(HistoryEnum.COMMENTS_OK, postDTOResponse.id()));
        enabled(postDTOResponse);
    }
    @Async
    public void enabled(PostDTOResponse postDTOResponse){
        log.info("ENABLED");
        historyRepository.save(new History(HistoryEnum.ENABLED, postDTOResponse.id()));
    }
    @Async
    public void disabled(PostDTOResponse postDTOResponse){
        findById(postDTOResponse.id());
        log.info("DISABLED");
        List<History> historyList = historyRepository.findAllByPostId(postDTOResponse.id());
        if(historyList.get(historyList.size()- 1).getStatus().equals(HistoryEnum.valueOf("ENABLED"))
                || historyList.get(historyList.size()- 1).getStatus().equals(HistoryEnum.valueOf("FAILED"))){
            historyRepository.save(new History(HistoryEnum.DISABLED, postDTOResponse.id()));
        }else{
            throw new ResourceNotFoundException("This post only can be disabled if it is enabled or failed");
        }

    }
    @Async
    public void updatingPost(PostDTOResponse postDTOResponse) {
        findById(postDTOResponse.id());
        List<History> historyList = searchHistory(postDTOResponse.id());
        if(historyList.get(historyList.size()- 1).getStatus().equals(HistoryEnum.valueOf("ENABLED"))
                || historyList.get(historyList.size()- 1).getStatus().equals(HistoryEnum.valueOf("DISABLED"))){
            historyRepository.save(new History(HistoryEnum.UPDATING, postDTOResponse.id()));
            log.info("UPDATING");
            findPost(postDTOResponse);
        }else{
            throw new ResourceNotFoundException("This post can only be updated if it status is ENABLED or DISABLED and it exists");
        }
    }
    @Async
    private void failedPost(PostDTOResponse postDTOResponse) {

        log.info("FAILED");
        historyRepository.save(new History(HistoryEnum.FAILED, postDTOResponse.id()));
        disabled(postDTOResponse);
    }
    public List<PostDTOResponse> findAll(){
         return repository.findAll().stream().map(PostDTOResponse::new).toList();
    }


    public void verifyPostId(Long postId){
        repository.findById(postId).ifPresent(post -> {
            throw new ResourceNotFoundException("Post already registered on system");
        });
    }
    public List<History> searchHistory(Long postId){
        return historyRepository.findAllByPostId(postId);
    }

    public PostDTOResponse findById(Long postId) {
        return new PostDTOResponse(repository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("")));
    }
}
