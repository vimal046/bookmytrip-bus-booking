package com.bookmytrip.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmytrip.dto.BookingRequestDTO;
import com.bookmytrip.enums.BookingStatus;
import com.bookmytrip.exception.InsufficientSeatsException;
import com.bookmytrip.exception.ResourceNotFoundException;
import com.bookmytrip.model.Booking;
import com.bookmytrip.model.Schedule;
import com.bookmytrip.model.User;
import com.bookmytrip.repository.BookingRepository;
import com.bookmytrip.repository.ScheduleRepository;
import com.bookmytrip.repository.UserRepository;
import com.bookmytrip.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;
	private final ScheduleRepository scheduleRepository;
	private final UserRepository userRepository;

	@Override
	public Booking createBooking(BookingRequestDTO dto,
			Long userId) {
		log.info("Creating booking for user: {} on schedule: {}", userId, dto.getScheduleId());

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
				.orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + dto.getScheduleId()));

		// check if enough seats available
		if (schedule.getAvailableSeats() < dto.getSeatsBooked()) {
			throw new InsufficientSeatsException("Only " + schedule.getAvailableSeats() + " seats available");
		}

		// calculate total fare
		Double totalFare = schedule.getFare() * dto.getSeatsBooked();

		// Create booking
		Booking booking = Booking.builder()
				.user(user)
				.schedule(schedule)
				.seatsBooked(dto.getSeatsBooked())
				.totalFare(totalFare)
				.bookingDate(LocalDateTime.now())
				.status(BookingStatus.CONFIRMED)
				.build();

		// update available seats
		schedule.setAvailableSeats(schedule.getAvailableSeats() - dto.getSeatsBooked());
		scheduleRepository.save(schedule);

		Booking savedBooking = bookingRepository.save(booking);
		log.info("Booking created successfully: {}", savedBooking.getBookingReference());
		return savedBooking;
	}

	@Override
	public Booking cancelBooking(Long bookingId,
			Long userId) {
		log.info("Cancelling booking: {} by user: {}", bookingId, userId);

		Booking booking = getBookingById(bookingId);

		// verify booking belongs to user
		if (booking.getUser()
				.getId()
				.equals(userId)) {
			throw new IllegalArgumentException("you can only cancel your own bookings");
		}

		// check if already cancelled
		if (booking.getStatus() == BookingStatus.CANCELLED) {
			throw new IllegalStateException("Booking is already cancelled");
		}

		// update booking status
		booking.setStatus(BookingStatus.CANCELLED);

		// return seats to schedule
		Schedule schedule = booking.getSchedule();
		schedule.setAvailableSeats(schedule.getAvailableSeats() + booking.getSeatsBooked());
		scheduleRepository.save(schedule);

		Booking cancelledBooking = bookingRepository.save(booking);
		log.info("Booking cancelled successfully: {}", bookingId);
		return cancelledBooking;
	}

	@Override
	@Transactional(readOnly = true)
	public Booking getBookingById(Long id) {
		return bookingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Booking> getBookingsByUser(Long userId) {
		return bookingRepository.findByUserIdOrderByBookingDateDesc(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Booking getBookingByReference(String reference) {
		return bookingRepository.findByBookingReference(reference)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with references: " + reference));
	}

}
