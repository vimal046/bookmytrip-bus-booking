package com.bookmytrip.model;

import java.time.LocalDateTime;

import com.bookmytrip.enums.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*Many bookings belongs to one user*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	/*Many bookings belongs to one schedule*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id",nullable = false)
	private Schedule schedule;
	
	@NotNull(message = "Number of seats is required")
	@Positive(message = "Number seats must be positive")
	@Column(nullable = false)
	private Integer seatsBooked;
	
	@NotNull(message = "Total fare is required")
	@Positive(message = "Total fare must be positive")
	@Column(nullable = false)
	private Double totalFare;
	
	@Column(nullable = false)
	private LocalDateTime bookingDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookingStatus status = BookingStatus.CONFIRMED;
	
	@Column(unique = true)
	private String bookingReference;

	/* Set booking date and reference before persisting */
	@PrePersist
	public void prePersist() {
		if (bookingDate == null) {
			bookingDate = LocalDateTime.now();
		}
		if (bookingReference == null) {
			bookingReference = generateBookingReference();
		}
	}

	/* Generate unique booking reference */
	private String generateBookingReference() {
		return "BKG" + System.currentTimeMillis();
	}
}
