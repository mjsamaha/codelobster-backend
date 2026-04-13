package com.mjsamaha.codelobster.rating;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingHistoryRepository extends JpaRepository<RatingHistory, Long> {

    List<RatingHistory> findTop50ByUserIdOrderByChangedAtDesc(Long userId);
}
