package com.bookmytrip.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;
import org.springframework.web.service.annotation.PostExchange;

import com.bookmytrip.enums.BusStatus;
import com.bookmytrip.enums.BusType;

import jakarta.persistence.CascadeType;
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
@Table(name = "buses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Bus number is required")
	@Column(unique = true, nullable = false)
	private String busNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BusType busType;

	@NotNull(message = "Total seats is required")
	@Positive(message = "Total seats must be posetive")
	@Column(nullable = false)
	private Integer totalSeats;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BusStatus status = BusStatus.ACTIVE;

	/* Many buses can belongs to one route */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_id", nullable = false)
	private Route route;

	@OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Schedule> schedules = new ArrayList<>();
	
}
