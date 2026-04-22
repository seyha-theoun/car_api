package org.example.car.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.car.dto.booking.BookingRequest;
import org.example.car.dto.booking.BookingResponse;
import org.example.car.entity.*;
import org.example.car.exception.ForbiddenException;
import org.example.car.exception.ResourceNotFoundException;
import org.example.car.repository.BookingRepository;
import org.example.car.repository.CarRepository;
import org.example.car.repository.UserRepository;
import org.example.car.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request, String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Car car = carRepository.findByIdAndDeletedFalse(request.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        Booking booking = Booking.builder()
                .user(user)
                .car(car)
                .bookingDate(request.getBookingDate())
                .status(BookingStatus.PENDING)
                .build();

        return toResponse(bookingRepository.save(booking));
    }

    @Override
    public List<BookingResponse> getMyBookings(String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return bookingRepository.findByUserOrderByBookingDateDesc(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public BookingResponse updateBookingStatus(Long bookingId, BookingStatus status, String currentUserEmail) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!booking.getCar().getSeller().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("Only the car owner can update booking status");
        }

        booking.setStatus(status);
        return toResponse(bookingRepository.save(booking));
    }

    private BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .userName(booking.getUser().getName())
                .carId(booking.getCar().getId())
                .carTitle(booking.getCar().getTitle())
                .bookingDate(booking.getBookingDate())
                .status(booking.getStatus().name())
                .build();
    }
}

