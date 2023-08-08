package com.uol.pb.challenge3.repository;

import com.uol.pb.challenge3.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
