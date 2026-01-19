package com.bookmytrip.service;

import java.util.List;

import com.bookmytrip.dto.BookingRequestDTO;
import com.bookmytrip.model.Booking;

public interface BookingService {

	Booking createBooking(BookingRequestDTO dto, Long userId);

	Booking cancelBooking(Long bookingId, Long userId);

	Booking getBookingById(Long id);

	List<Booking> getBookingsByUser(Long userId);

	List<Booking> getAllBookings();

	Booking getBookingByReference(String reference);
}
