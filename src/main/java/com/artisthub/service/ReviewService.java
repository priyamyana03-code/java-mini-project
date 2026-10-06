package com.artisthub.service;

import com.artisthub.entity.Review;
import com.artisthub.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAllByOrderByIdDesc();
    }

    public Review saveReview(Review review) {
        if (review.getStudentName() == null || review.getStudentName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is required.");
        }
        if (review.getClassName() == null || review.getClassName().trim().isEmpty()) {
            throw new IllegalArgumentException("Class name is required.");
        }
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be an integer between 1 and 5.");
        }
        if (review.getComment() == null || review.getComment().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty.");
        }

        review.setStudentName(review.getStudentName().trim());
        review.setClassName(review.getClassName().trim());
        review.setComment(review.getComment().trim());
        if (review.getReviewDate() == null) {
            review.setReviewDate(LocalDate.now());
        }

        return reviewRepository.save(review);
    }

    public long getReviewCount() {
        return reviewRepository.count();
    }
}
