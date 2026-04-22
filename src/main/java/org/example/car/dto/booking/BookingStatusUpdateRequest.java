package org.example.car.dto.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.car.entity.BookingStatus;

@Data
public class BookingStatusUpdateRequest {

    @NotNull
    private BookingStatus status;
}

