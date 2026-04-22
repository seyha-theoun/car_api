package org.example.car.service;

import org.example.car.dto.review.ReviewRequest;
import org.example.car.dto.review.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request, String currentUserEmail);

    List<ReviewResponse> getSellerReviews(Long sellerId);
}

