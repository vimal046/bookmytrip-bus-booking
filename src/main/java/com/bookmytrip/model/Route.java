package com.bookmytrip.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Source city is required")
	@Column(nullable = false)
	private String source;

	@NotBlank(message = "Destinaton city is required")
	@Column(nullable = false)
	private String destination;

	@NotNull(message = "Distance is required")
	@Positive(message = "Distance must be posetive")
	@Column(nullable = false)
	private Double distanceKm;

	@NotBlank(message = "Duration is required")
	@Column(nullable = false)
	private String duration;

	@OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Bus> buses = new ArrayList<>();

	@Override
	public String toString() {
		return source + " -> " + destination + " (" + distanceKm + " km)";
	}
}
