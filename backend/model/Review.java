package com.skillswap.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table (name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "Reviewer_id", nullable = false)
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "targetUser_id", nullable = false)
    private User targetUser;

    @Column(nullable = true) //u can not have a comment so yeah, I probably don't even need to specify the equal true, but I like it
    private String comment;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}
