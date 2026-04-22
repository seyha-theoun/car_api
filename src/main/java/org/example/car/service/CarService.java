package org.example.car.service;

import org.example.car.dto.car.CarCreateRequest;
import org.example.car.dto.car.CarResponse;
import org.example.car.dto.car.CarUpdateRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface CarService {
    CarResponse createCar(CarCreateRequest request, String currentUserEmail);

    Page<CarResponse> getCars(int page, int size);

    CarResponse getCarById(Long id);

    CarResponse updateCar(Long id, CarUpdateRequest request, String currentUserEmail);

    void deleteCar(Long id, String currentUserEmail);

    Page<CarResponse> searchCars(String brand, BigDecimal priceMin, BigDecimal priceMax, Integer year, int page, int size);
}

