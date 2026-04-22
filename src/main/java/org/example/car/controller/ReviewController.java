package org.example.car.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.car.dto.review.ReviewRequest;
import org.example.car.dto.review.ReviewResponse;
import org.example.car.service.ReviewService;
import org.example.car.util.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.createReview(request, SecurityUtils.currentUserEmail()));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ReviewResponse>> getSellerReviews(@PathVariable Long sellerId) {
        return ResponseEntity.ok(reviewService.getSellerReviews(sellerId));
    }
}

