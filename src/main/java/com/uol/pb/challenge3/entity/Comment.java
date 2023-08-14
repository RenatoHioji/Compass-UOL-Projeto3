package com.uol.pb.challenge3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comments")
@JsonIgnoreProperties("post")
public class Comment {
    @Id
    private Long id;
    @Column(length = 500)
    private String body;
    @Column(name = "post_id")
    Long postId;


    public Comment(String body, Long postId, Long commentId) {
        this.body = body;
        this.postId = postId;
        this.id = commentId;
    }

}

