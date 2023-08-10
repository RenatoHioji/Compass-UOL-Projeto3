package com.uol.pb.challenge3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uol.pb.challenge3.dto.response.PostDTOResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
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
@JsonIgnoreProperties({"history", "comment"})
public class Post {

    @Id
    Long id;
    String title;
    String body;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Comment> comment = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    private Set<History> history = new HashSet<>();

    public Post(Long postId) {
        this.id = postId;
        this.title = null;
        this.body = null;
    }
    public Post(PostDTOResponse postDTOResponse){
        this.id = postDTOResponse.id();
        this.title = postDTOResponse.title();
        this.body= postDTOResponse.body();
        this.comment = postDTOResponse.comment();
        this.history = postDTOResponse.history();
    }
}
