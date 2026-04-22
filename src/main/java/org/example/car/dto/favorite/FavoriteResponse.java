package org.example.car.dto.favorite;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FavoriteResponse {
    private Long favoriteId;
    private Long carId;
    private String title;
    private String brand;
    private String model;
    private BigDecimal price;
    private String status;
}

