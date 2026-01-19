package com.bookmytrip.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

	@NotNull(message = "Bus ID is required")
	private Long busId;
	
	@NotNull(message = "Date is required")
	private LocalDate date;
	
	@NotNull(message = "Departure time is required")
	private LocalTime departureTime;
	
	@NotNull(message = "Arrival time is required")
	private LocalTime arrivalTime;
	
	@NotNull(message = "Fare is required")
	@Positive
	private Double fare;
}
