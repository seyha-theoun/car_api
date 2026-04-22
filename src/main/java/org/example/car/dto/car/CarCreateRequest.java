package org.example.car.dto.car;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Min(1900)
    private Integer year;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Long mileage;

    @NotBlank
    private String fuelType;

    @NotBlank
    private String transmission;

    @NotBlank
    private String location;

    private String description;

    private List<@NotBlank String> imageUrls;
}

