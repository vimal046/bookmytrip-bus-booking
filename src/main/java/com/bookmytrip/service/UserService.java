package com.bookmytrip.service;

import java.util.List;

import com.bookmytrip.dto.UserRegistrationDTO;
import com.bookmytrip.model.User;

public interface UserService {

	User registerUser(UserRegistrationDTO dto);

	User getUserByEmail(String email);

	User getUserById(Long id);

	List<User> getAllUser();

	void deleteUser(Long id);
}
