package com.bookmytrip.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*Many schedules belongs to one bus*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bus_id",nullable = false)
	private Bus bus;
	
	@NotNull(message = "Date is required")
	@Column(nullable = false)
	private LocalDate date;
	
	@NotNull(message = "Departure time is required")
	@Column(nullable = false)
	private LocalTime departureTime;
	
	@NotNull(message = "Arrival time is required")
	@Column(nullable = false)
	private LocalTime arrivalTime;
	
	@NotNull(message = "Fare is required")
	@Positive(message = "Fare must be posetive")
	@Column(nullable = false)
	private Double fare;
	
	@Column(nullable = false)
	private Integer availableSeats;
	
	/*One schedule can have multiple bookings*/
	@OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL)
	@Builder.Default
	private List<Booking> bookings = new ArrayList<>();
	
	
	
	/*Initialize available seats based on bus capacity*/
	@PrePersist
	@PreUpdate
	public void initializeAvailableSeats() {
		if(availableSeats == null && bus !=null) {
			availableSeats=bus.getTotalSeats();
		}
	}
}
