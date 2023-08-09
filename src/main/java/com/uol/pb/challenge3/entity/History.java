package com.uol.pb.challenge3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.uol.pb.challenge3.entity.enums.HistoryEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="history")
@JsonIgnoreProperties("post")

public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Instant instant;
    @Enumerated(EnumType.STRING)
    HistoryEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_id", nullable = false)
    private Post post;
    public History(Instant now, String created, Post post) {
        this.instant = now;
        this.status = HistoryEnum.valueOf(created);
        this.post = post;
        post.getHistory().add(this);
    }
}
