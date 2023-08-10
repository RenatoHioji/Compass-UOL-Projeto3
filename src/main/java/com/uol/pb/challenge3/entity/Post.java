package com.uol.pb.challenge3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts")
@JsonIgnoreProperties({"history", "comment"})
public class Post {

    @Id
    private Long id;
    private String title;
    private String body;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Set<History> histories = new HashSet<>();

    public Post(Long postId) {
        this.id = postId;
        this.comments = new HashSet<>();
        this.histories = new HashSet<>();
    }
    public Post(PostDTOResponse postDTOResponse){
        this.id = postDTOResponse.id();
        this.title = postDTOResponse.title();
        this.body= postDTOResponse.body();
        this.comments = postDTOResponse.comment();
        this.histories = postDTOResponse.history();
    }
}
