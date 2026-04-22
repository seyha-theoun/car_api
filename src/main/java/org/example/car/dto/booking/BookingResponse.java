package org.example.car.dto.booking;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long carId;
    private String carTitle;
    private LocalDateTime bookingDate;
    private String status;
}

