package org.example.car.service;

import org.example.car.dto.booking.BookingRequest;
import org.example.car.dto.booking.BookingResponse;
import org.example.car.entity.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request, String currentUserEmail);

    List<BookingResponse> getMyBookings(String currentUserEmail);

    BookingResponse updateBookingStatus(Long bookingId, BookingStatus status, String currentUserEmail);
}

