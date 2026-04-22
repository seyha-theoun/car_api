package org.example.car.repository;

import org.example.car.entity.Review;
import org.example.car.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findBySellerOrderByCreatedAtDesc(User seller);
}

