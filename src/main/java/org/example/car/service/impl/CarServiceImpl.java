package org.example.car.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.car.dto.car.CarCreateRequest;
import org.example.car.dto.car.CarResponse;
import org.example.car.dto.car.CarUpdateRequest;
import org.example.car.entity.*;
import org.example.car.exception.ForbiddenException;
import org.example.car.exception.ResourceNotFoundException;
import org.example.car.repository.*;
import org.example.car.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarImageRepository carImageRepository;

    @Override
    @Transactional
    public CarResponse createCar(CarCreateRequest request, String currentUserEmail) {
        User seller = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (seller.getRole() != Role.SELLER) {
            throw new ForbiddenException("Only SELLER users can post cars");
        }

        Car car = Car.builder()
                .title(request.getTitle())
                .brand(request.getBrand())
                .model(request.getModel())
                .year(request.getYear())
                .price(request.getPrice())
                .mileage(request.getMileage())
                .fuelType(request.getFuelType())
                .transmission(request.getTransmission())
                .location(request.getLocation())
                .description(request.getDescription())
                .status(CarStatus.AVAILABLE)
                .deleted(false)
                .seller(seller)
                .build();

        carRepository.save(car);
        saveImages(car, request.getImageUrls());
        return toResponse(car);
    }

    @Override
    public Page<CarResponse> getCars(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.findAllByDeletedFalse(pageable).map(this::toResponse);
    }

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAllByDeletedFalseOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CarResponse getCarById(Long id) {
        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));
        return toResponse(car);
    }

    @Override
    @Transactional
    public CarResponse updateCar(Long id, CarUpdateRequest request, String currentUserEmail) {
        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateOwner(car, currentUser);

        if (request.getTitle() != null) car.setTitle(request.getTitle());
        if (request.getBrand() != null) car.setBrand(request.getBrand());
        if (request.getModel() != null) car.setModel(request.getModel());
        if (request.getYear() != null) car.setYear(request.getYear());
        if (request.getPrice() != null) car.setPrice(request.getPrice());
        if (request.getMileage() != null) car.setMileage(request.getMileage());
        if (request.getFuelType() != null) car.setFuelType(request.getFuelType());
        if (request.getTransmission() != null) car.setTransmission(request.getTransmission());
        if (request.getLocation() != null) car.setLocation(request.getLocation());
        if (request.getDescription() != null) car.setDescription(request.getDescription());

        if (request.getImageUrls() != null) {
            carImageRepository.deleteByCar(car);
            saveImages(car, request.getImageUrls());
        }

        carRepository.save(car);
        return toResponse(car);
    }

    @Override
    @Transactional
    public void deleteCar(Long id, String currentUserEmail) {
        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateOwner(car, currentUser);
        car.setDeleted(true);
        carRepository.save(car);
    }

    @Override
    public Page<CarResponse> searchCars(String brand, BigDecimal priceMin, BigDecimal priceMax, Integer year, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Car> spec = Specification.where(CarSpecifications.notDeleted())
                .and(CarSpecifications.brandEquals(brand))
                .and(CarSpecifications.priceGreaterThanOrEqual(priceMin))
                .and(CarSpecifications.priceLessThanOrEqual(priceMax))
                .and(CarSpecifications.yearEquals(year));

        return carRepository.findAll(spec, pageable).map(this::toResponse);
    }

    private void saveImages(Car car, List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        List<CarImage> images = imageUrls.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(url -> !url.isBlank())
                .map(url -> CarImage.builder().car(car).imageUrl(url).build())
                .toList();

        if (!images.isEmpty()) {
            carImageRepository.saveAll(images);
        }
    }

    private void validateOwner(Car car, User currentUser) {
        if (!car.getSeller().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("Only the owner can modify this car");
        }
    }

    private CarResponse toResponse(Car car) {
        List<String> imageUrls = carImageRepository.findByCar(car)
                .stream()
                .map(CarImage::getImageUrl)
                .toList();

        return CarResponse.builder()
                .id(car.getId())
                .title(car.getTitle())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .price(car.getPrice())
                .mileage(car.getMileage())
                .fuelType(car.getFuelType())
                .transmission(car.getTransmission())
                .location(car.getLocation())
                .description(car.getDescription())
                .status(car.getStatus().name())
                .createdAt(car.getCreatedAt())
                .sellerId(car.getSeller().getId())
                .sellerName(car.getSeller().getName())
                .imageUrls(imageUrls)
                .build();
    }
}

