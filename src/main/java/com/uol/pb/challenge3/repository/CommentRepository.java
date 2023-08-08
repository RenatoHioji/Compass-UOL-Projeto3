package com.uol.pb.challenge3.repository;

import com.uol.pb.challenge3.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
