package org.example.car.service;

import org.example.car.dto.favorite.FavoriteResponse;

import java.util.List;

public interface FavoriteService {
    FavoriteResponse addFavorite(Long carId, String currentUserEmail);

    void removeFavorite(Long carId, String currentUserEmail);

    List<FavoriteResponse> getMyFavorites(String currentUserEmail);
}

