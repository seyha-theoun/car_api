package org.example.car.repository;

import org.example.car.entity.Car;
import org.example.car.entity.Favorite;
import org.example.car.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserAndCar(User user, Car car);

    List<Favorite> findByUser(User user);

    boolean existsByUserAndCar(User user, Car car);
}

