package com.bookmytrip.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmytrip.dto.UserRegistrationDTO;
import com.bookmytrip.enums.Role;
import com.bookmytrip.exception.ResourceAlreadyExistsException;
import com.bookmytrip.exception.ResourceNotFoundException;
import com.bookmytrip.model.User;
import com.bookmytrip.repository.UserRepository;
import com.bookmytrip.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(UserRegistrationDTO dto) {
		log.info("Registering new user: {}", dto.getEmail());

		// check if email already exists
		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new ResourceAlreadyExistsException("Email already registered: " + dto.getEmail());
		}

		// validate password confirmation
		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
			throw new IllegalArgumentException("Password do not match");
		}

		// create new user
		User user = User.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.password(passwordEncoder.encode(dto.getPassword()))
				.role(Role.USER)
				.enabled(true)
				.build();

		User savedUser = userRepository.save(user);
		log.info("User registered successfully: {}", savedUser.getEmail());
		return savedUser;
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(Long id) {
		User user = getUserById(id);
		userRepository.delete(user);
		log.info("User deleted: {}", id);
	}

}
