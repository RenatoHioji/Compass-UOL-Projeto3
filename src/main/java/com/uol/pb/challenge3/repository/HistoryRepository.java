package com.uol.pb.challenge3.repository;

import com.uol.pb.challenge3.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findAllByPostId(Long postId);
}
