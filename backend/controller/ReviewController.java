package com.skillswap.backend.controller;

import com.skillswap.backend.model.Review;
import com.skillswap.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/{userId}")
    public List<Review> getReviews(@PathVariable Long userId) {
        return reviewService.getAllReviewsForUser(userId);
    }


    // We use @RequestParam to get data from the URL or form
    @PostMapping("/add")
    public Review addReview(@RequestParam Long reviewerId,
                            @RequestParam Long targetId,
                            @RequestParam String comment,
                            @RequestParam int rating) {

        return reviewService.addReview(reviewerId, targetId, comment, rating);
    }
}