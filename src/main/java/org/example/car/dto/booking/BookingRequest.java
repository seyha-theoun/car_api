package org.example.car.dto.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {

    @NotNull
    private Long carId;

    private LocalDateTime bookingDate;
}

