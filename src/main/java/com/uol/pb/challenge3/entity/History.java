package com.uol.pb.challenge3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.uol.pb.challenge3.entity.enums.HistoryEnum;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="history")
@JsonIgnoreProperties("post")

public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Instant date;

    @Enumerated(EnumType.STRING)
    private HistoryEnum status;

    @Column(name = "post_id")
    Long postId;

    public History(HistoryEnum status, Long postId) {
        this.status = status;
        this.postId = postId;
        this.date = Instant.now();
    }

}
