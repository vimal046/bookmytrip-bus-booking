package com.bookmytrip.dto;

import jakarta.validation.constraints.NotBlank;
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
public class BusDTO {

	@NotBlank(message = "Bus number is required")
	private String busNumber;

	@NotBlank(message = "Bus type is required")
	private String busType;

	@NotNull(message = "Total seats is required")
	@Positive(message = "Seats must be positive number")
	private Integer totalSeats;

	@NotNull(message = "Rote ID is required")
	private Long routeId;
}
