package org.example.car.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.car.dto.review.ReviewRequest;
import org.example.car.dto.review.ReviewResponse;
import org.example.car.entity.Review;
import org.example.car.entity.User;
import org.example.car.exception.BadRequestException;
import org.example.car.exception.ResourceNotFoundException;
import org.example.car.repository.ReviewRepository;
import org.example.car.repository.UserRepository;
import org.example.car.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReviewResponse createReview(ReviewRequest request, String currentUserEmail) {
        User reviewer = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        if (reviewer.getId().equals(seller.getId())) {
            throw new BadRequestException("You cannot review yourself");
        }

        Review review = Review.builder()
                .reviewer(reviewer)
                .seller(seller)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        return toResponse(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponse> getSellerReviews(Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        return reviewRepository.findBySellerOrderByCreatedAtDesc(seller)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .reviewerId(review.getReviewer().getId())
                .reviewerName(review.getReviewer().getName())
                .sellerId(review.getSeller().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

