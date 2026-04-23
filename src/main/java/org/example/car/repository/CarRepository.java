package org.example.car.repository;

import org.example.car.entity.Car;
import org.example.car.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
    Page<Car> findAllByDeletedFalse(Pageable pageable);

    List<Car> findAllByDeletedFalseOrderByCreatedAtDesc();

    Optional<Car> findByIdAndDeletedFalse(Long id);

    Page<Car> findBySellerAndDeletedFalse(User seller, Pageable pageable);
}

