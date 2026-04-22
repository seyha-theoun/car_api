package org.example.car.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.car.dto.booking.BookingRequest;
import org.example.car.dto.booking.BookingResponse;
import org.example.car.dto.booking.BookingStatusUpdateRequest;
import org.example.car.service.BookingService;
import org.example.car.util.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request, SecurityUtils.currentUserEmail()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings(SecurityUtils.currentUserEmail()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BookingResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody BookingStatusUpdateRequest request
    ) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, request.getStatus(), SecurityUtils.currentUserEmail()));
    }
}

