package com.skillswap.backend.service;

import com.skillswap.backend.model.Review;
import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.ReviewRepository;
import com.skillswap.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Review> getAllReviewsForUser(Long userId){
        return reviewRepository.findByTargetUserId(userId);
    }

    public Review addReview(Long reviewerId, Long targetUserId, String comment, int rating) {

        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5 only.");
        }

        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        if (reviewer.getId() == (targetUser.getId())) {
            throw new RuntimeException("are you trying to reviewing yourself? that's cheating!");

        }

        Review review = new Review();
        review.setReviewer(reviewer);
        review.setTargetUser(targetUser);
        review.setComment(comment);
        review.setRating(rating);

        return reviewRepository.save(review);

    }
}
