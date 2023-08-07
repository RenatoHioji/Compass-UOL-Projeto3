package com.uol.pb.challenge3.dto.response;

import com.uol.pb.challenge3.entity.Comment;
import com.uol.pb.challenge3.entity.History;

import java.util.Set;

public record PostDTOResponse(
        Long id,
        String title,
        String body,
        Set<Comment> comments,
        Set<History> historySet
) {
}
