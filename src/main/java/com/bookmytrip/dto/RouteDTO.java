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
public class RouteDTO {

	@NotBlank(message = "Source is required")
	private String source;

	@NotBlank(message = "Destination is required")
	private String destination;

	@NotNull(message = "Distance is required")
	@Positive(message = "Distance must be a positive number")
	private Double distanceKm;

	@NotBlank(message = "Duration is required")
	private String duration;
}
