package org.example.car;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.car.dto.review.ReviewRequest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReviewRequestValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void ratingAboveFiveIsRejected() {
        ReviewRequest request = new ReviewRequest();
        request.setSellerId(1L);
        request.setRating(10);

        Set<ConstraintViolation<ReviewRequest>> violations = validator.validate(request);

        assertTrue(
                violations.stream().anyMatch(v -> "rating".equals(v.getPropertyPath().toString())),
                "Expected validation error for rating > 5"
        );
    }
}

