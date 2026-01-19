package com.bookmytrip.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {

	@NotNull(message = "Schedule ID is required")
	private Long scheduleId;
	
	@NotNull(message = "Number of seats is required")
	@Min(value = 1,message = "At least 1 seat must be booked")
	@Max(value = 6, message = "Maximum 6 seat can be booked at once")
	private Integer seatsBooked;
}
