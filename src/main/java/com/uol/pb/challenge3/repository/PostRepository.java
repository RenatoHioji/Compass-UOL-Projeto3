package com.uol.pb.challenge3.repository;

import com.uol.pb.challenge3.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
