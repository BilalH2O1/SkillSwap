package com.skillswap.backend.repository;

import com.skillswap.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTargetUserId(Long userId);

    List<Review> findByReviewerId (Long userId);
}
