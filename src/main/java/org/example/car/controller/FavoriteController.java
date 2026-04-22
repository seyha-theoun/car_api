package org.example.car.controller;

import lombok.RequiredArgsConstructor;
import org.example.car.dto.favorite.FavoriteResponse;
import org.example.car.service.FavoriteService;
import org.example.car.util.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{carId}")
    public ResponseEntity<FavoriteResponse> addFavorite(@PathVariable Long carId) {
        return ResponseEntity.ok(favoriteService.addFavorite(carId, SecurityUtils.currentUserEmail()));
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long carId) {
        favoriteService.removeFavorite(carId, SecurityUtils.currentUserEmail());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites() {
        return ResponseEntity.ok(favoriteService.getMyFavorites(SecurityUtils.currentUserEmail()));
    }
}

