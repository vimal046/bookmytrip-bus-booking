package com.bookmytrip.model;

import java.util.ArrayList;
import java.util.List;

import com.bookmytrip.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	@Column(nullable = false)
	private String name;

	@Email(message = "Invalid Email format")
	@Column(unique = true, nullable = false)
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be atleast 6 characters")
	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.USER;

	@Column(nullable = false)
	private Boolean enabled;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Booking> bookings = new ArrayList<>();

}
