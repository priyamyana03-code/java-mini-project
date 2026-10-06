package com.artisthub.controller;

import com.artisthub.entity.Review;
import com.artisthub.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        Map<String, Object> response = new HashMap<>();

        if (review.getStudentName() == null || review.getStudentName().trim().isEmpty()) {
            response.put("error", "Student name cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        if (review.getClassName() == null || review.getClassName().trim().isEmpty()) {
            response.put("error", "Class name cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            response.put("error", "Rating must be between 1 and 5.");
            return ResponseEntity.badRequest().body(response);
        }
        if (review.getComment() == null || review.getComment().trim().isEmpty()) {
            response.put("error", "Review comment cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Review savedReview = reviewService.saveReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("error", "Failed to save review: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
