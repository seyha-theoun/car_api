package org.example.car.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.car.dto.car.CarCreateRequest;
import org.example.car.dto.car.CarResponse;
import org.example.car.dto.car.CarUpdateRequest;
import org.example.car.service.CarService;
import org.example.car.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CarCreateRequest request) {
        return ResponseEntity.ok(carService.createCar(request, SecurityUtils.currentUserEmail()));
    }

    @GetMapping
    public ResponseEntity<Page<CarResponse>> getCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(carService.getCars(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCar(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @Valid @RequestBody CarUpdateRequest request) {
        return ResponseEntity.ok(carService.updateCar(id, request, SecurityUtils.currentUserEmail()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id, SecurityUtils.currentUserEmail());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CarResponse>> searchCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(carService.searchCars(brand, priceMin, priceMax, year, page, size));
    }
}

