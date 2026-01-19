package com.bookmytrip.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookmytrip.enums.BookingStatus;
import com.bookmytrip.model.Booking;
import com.bookmytrip.model.Schedule;
import com.bookmytrip.model.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUser(User user);

	List<Booking> findBySchedule(Schedule schedule);

	Optional<Booking> findByBookingReference(String bookingReference);

	List<Booking> findBystatus(BookingStatus status);

	@Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.bookingDate DESC")
	List<Booking> findByUserIdOrderByBookingDateDesc(@Param("userId") Long userId);
}
