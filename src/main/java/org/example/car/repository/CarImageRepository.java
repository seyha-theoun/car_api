package org.example.car.repository;

import org.example.car.entity.Car;
import org.example.car.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {
    List<CarImage> findByCar(Car car);

    void deleteByCar(Car car);
}

