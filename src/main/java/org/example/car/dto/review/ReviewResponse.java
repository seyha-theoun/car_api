package org.example.car.dto.review;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private Long reviewerId;
    private String reviewerName;
    private Long sellerId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}

