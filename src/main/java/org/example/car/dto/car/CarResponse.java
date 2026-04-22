package org.example.car.dto.car;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CarResponse {
    private Long id;
    private String title;
    private String brand;
    private String model;
    private Integer year;
    private BigDecimal price;
    private Long mileage;
    private String fuelType;
    private String transmission;
    private String location;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private Long sellerId;
    private String sellerName;
    private List<String> imageUrls;
}

