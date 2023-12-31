package com.uol.pb.challenge3.dto.response;

import com.uol.pb.challenge3.entity.Comment;
import com.uol.pb.challenge3.entity.History;
import com.uol.pb.challenge3.entity.Post;

import java.util.Set;

    public record PostDTOResponse(
            Long id,
            String title,
            String body,
            Set<Comment> comment,
            Set<History> history
        ) {

        public PostDTOResponse(Post post) {
            this(post.getId(), post.getTitle(), post.getBody(), post.getComments(), post.getHistories());
        }
    }
