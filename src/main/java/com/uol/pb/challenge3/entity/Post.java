package com.uol.pb.challenge3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts")
@JsonIgnoreProperties("history")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String body;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Comment> comment = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<History> history = new HashSet<>();
    public Post(Long postId) {
        this.id = postId;
        this.title = null;
        this.body = null;

    }
}
