package org.example.car.dto.car;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarUpdateRequest {
    private String title;
    private String brand;
    private String model;

    @Min(1900)
    private Integer year;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Min(0)
    private Long mileage;

    private String fuelType;
    private String transmission;
    private String location;
    private String description;
    private List<String> imageUrls;
}

