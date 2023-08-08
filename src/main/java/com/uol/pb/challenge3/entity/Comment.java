package com.uol.pb.challenge3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comments")
@JsonIgnoreProperties("post")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String body;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    public Comment(String body, Post post) {
        this.body = body;
        this.post = post;
        post.getComment().add(this);
    }
}
