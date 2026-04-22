package org.example.car.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.car.dto.favorite.FavoriteResponse;
import org.example.car.entity.Car;
import org.example.car.entity.Favorite;
import org.example.car.entity.User;
import org.example.car.exception.BadRequestException;
import org.example.car.exception.ResourceNotFoundException;
import org.example.car.repository.CarRepository;
import org.example.car.repository.FavoriteRepository;
import org.example.car.repository.UserRepository;
import org.example.car.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    @Transactional
    public FavoriteResponse addFavorite(Long carId, String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Car car = carRepository.findByIdAndDeletedFalse(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        if (favoriteRepository.existsByUserAndCar(user, car)) {
            throw new BadRequestException("Car is already in favorites");
        }

        Favorite favorite = favoriteRepository.save(Favorite.builder().user(user).car(car).build());
        return toResponse(favorite);
    }

    @Override
    @Transactional
    public void removeFavorite(Long carId, String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Car car = carRepository.findByIdAndDeletedFalse(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        Favorite favorite = favoriteRepository.findByUserAndCar(user, car)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }

    @Override
    public List<FavoriteResponse> getMyFavorites(String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return favoriteRepository.findByUser(user).stream().map(this::toResponse).toList();
    }

    private FavoriteResponse toResponse(Favorite favorite) {
        return FavoriteResponse.builder()
                .favoriteId(favorite.getId())
                .carId(favorite.getCar().getId())
                .title(favorite.getCar().getTitle())
                .brand(favorite.getCar().getBrand())
                .model(favorite.getCar().getModel())
                .price(favorite.getCar().getPrice())
                .status(favorite.getCar().getStatus().name())
                .build();
    }
}

