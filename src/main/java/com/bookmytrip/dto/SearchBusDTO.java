package com.bookmytrip.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchBusDTO {

	@NotBlank(message = "Source city is required")
	private String source;

	@NotBlank(message = "Destination is required")
	private String destination;

	@NotNull(message = "Travel date is required")
	@Future(message = "Travel date must be in future")
	private LocalDate date;
}
