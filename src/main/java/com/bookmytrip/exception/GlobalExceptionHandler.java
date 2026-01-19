package com.bookmytrip.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/*Global Exception Handler for all controllers*/

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/* Handle resource not found exception */
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFound(ResourceNotFoundException ex,
			Model model) {
		log.error("Resource not found: {}", ex.getMessage());
		model.addAttribute("error", ex.getMessage());
		return "error/404";
	}

	/* Handle resource already exists exception */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public String handleResourceAlreadyExists(ResourceAlreadyExistsException ex,
			Model model) {
		log.error("Resource already exists: {}", ex.getMessage());
		model.addAttribute("error", ex.getMessage());
		return "error/error";
	}

	/* Handle Insufficient seats exception */
	@ExceptionHandler(InsufficientSeatsException.class)
	public String handleInsufficientSeats(InsufficientSeatsException ex,
			Model model) {
		log.error("Insufficient seats: {}", ex.getMessage());
		model.addAttribute("error", ex.getMessage());
		return "error/error";
	}

	/* Handle Validation Errors */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleValidationException(MethodArgumentNotValidException ex,
			Model model) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult()
				.getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		log.error("Validation errors: {}", errors);
		model.addAttribute("errors", errors);
		return "error/validation";
	}

	/* Handle Illegal Argument Exception */
	@ExceptionHandler(IllegalArgumentException.class)
	public String handleIllegalArgument(IllegalArgumentException ex,
			Model model) {
		log.error("Illegal argument: {}", ex.getMessage());
		model.addAttribute("error", ex.getMessage());
		return "error/error";
	}

	/* Handle Illegal state exception */
	public String handleIllegalState(IllegalStateException ex,
			Model model) {
		log.error("Illegal state: {}", ex.getMessage());
		model.addAttribute("error", ex.getMessage());
		return "error/error";
	}

	/* Handle all other exception */
	public String handleGenericException(Exception ex,
			Model model) {
		log.error("Unexpected error occured", ex);
		model.addAttribute("error", "An unexpected error occured. please try again later");
		return "error/error";
	}
}
