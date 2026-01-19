package com.bookmytrip.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookmytrip.dto.BusDTO;
import com.bookmytrip.dto.RouteDTO;
import com.bookmytrip.dto.ScheduleDTO;
import com.bookmytrip.enums.BookingStatus;
import com.bookmytrip.enums.BusType;
import com.bookmytrip.model.Booking;
import com.bookmytrip.model.Route;
import com.bookmytrip.service.BookingService;
import com.bookmytrip.service.BusService;
import com.bookmytrip.service.RouteService;
import com.bookmytrip.service.ScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Admin controller - Admin module endpoints

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {


	private final RouteService routeService;
	private final BusService busService;
	private final ScheduleService scheduleService;
	private final BookingService bookingService;
	
	// Admin dashboard
	@GetMapping("/dashboard")
	public String dashBoard(Model model) {
		model.addAttribute("totalRoutes",
				routeService.getAllRoutes()
						.size());
		model.addAttribute("totalBuses",
				busService.getAllBuses()
						.size());
		model.addAttribute("totalBookings",
				bookingService.getAllBookings()
						.size());
		return "admin/dashboard";
	}

	// ========== ROUTE MANAGEMENT ==========

	@GetMapping("/routes")
	public String listRoutes(Model model) {
		model.addAttribute("routes", routeService.getAllRoutes());
		return "admin/routes";
	}

	@GetMapping("/routes/add")
	public String showAddRouteForm(Model model) {
		model.addAttribute("route", new RouteDTO());
		return "admin/route-form";
	}

	@PostMapping("/routes/save")
	public String saveRoute(@Valid @ModelAttribute("route") RouteDTO dto,
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "admin/route-form";
		}

		try {
			routeService.createRoute(dto);
			redirectAttributes.addFlashAttribute("success", "Route added successfully!");
		} catch (Exception e) {
			log.error("Error saving route", e);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/admin/routes";
	}

	@GetMapping("/routes/edit/{id}")
	public String showEditRouteForm(@PathVariable Long id,
			Model model) {
		Route route = routeService.getRouteById(id);
		RouteDTO dto = RouteDTO.builder()
				.source(route.getSource())
				.destination(route.getDestination())
				.distanceKm(route.getDistanceKm())
				.duration(route.getDuration())
				.build();
		model.addAttribute("route", dto);
		model.addAttribute("routeId", id);
		return "admin/route-form";
	}

	@PostMapping("routes/update/{id}")
	public String updateRoute(@PathVariable Long id,
			@Valid @ModelAttribute("route") RouteDTO dto,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "admin/route-form";
		}

		try {
			routeService.updateRout(id, dto);
			redirectAttributes.addFlashAttribute("success", "Route updated successfully!");
		} catch (Exception e) {
			log.error("Error updating route", e);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/admin/routes";
	}

	@GetMapping("/routes/delete/{id}")
	public String deleteRoute(@PathVariable Long id,
			RedirectAttributes redirectAttributes) {
		try {
			routeService.deleteRoute(id);
			redirectAttributes.addFlashAttribute("success", "Route deleted successfully!");
		} catch (Exception e) {
			log.error("Error deleting route", e);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/admin/routes";
	}

	// ========== BUS MANAGEMENT ==========

	@GetMapping("/buses")
	public String listBuses(Model model) {
		model.addAttribute("buses", busService.getAllBuses());
		return "admin/buses";
	}

	@GetMapping("/buses/add")
	public String showAddBusForm(Model model) {
		model.addAttribute("bus", new BusDTO());
		model.addAttribute("routes", routeService.getAllRoutes());
		model.addAttribute("busTypes", BusType.values());
		return "admin/bus-form";
	}

	@PostMapping("buses/save")
	public String saveBus(@Valid @ModelAttribute("bus") BusDTO dto,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("routes", routeService.getAllRoutes());
			model.addAttribute("busTypes", BusType.values());
			return "admin/bus-form";
		}

		try {
			busService.createBus(dto);
			redirectAttributes.addFlashAttribute("success", "Bus Added successfully");
		} catch (Exception e) {
			log.error("Error saving bus", e);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/admin/buses";
	}

	@GetMapping("/buses/delete/{id}")
	public String deleteBus(@PathVariable Long id,
			RedirectAttributes redirectAttributes) {

		try {
			busService.deleteBus(id);
			redirectAttributes.addFlashAttribute("success", "Bus deleted successfully!");
		} catch (Exception e) {
			log.error("Error deleting bus", e);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/admin/buses";
	}

	// ========== SCHEDULE MANAGEMENT ==========

	@GetMapping("/schedules")
	public String listSchedules(Model model) {
		model.addAttribute("schedules", scheduleService.getAllSchedules());
		return "admin/schedules";
	}

	@GetMapping("/schedules/add")
	public String showAddScheduleForm(Model model) {
		model.addAttribute("schedule", new ScheduleDTO());
		model.addAttribute("buses", busService.getBusesWithAllRoutes());
		return "admin/schedule-form";
	}

	@PostMapping("/schedules/save")
	public String saveSchedule(@Valid @ModelAttribute("schedule") ScheduleDTO dto,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("buses", busService.getAllBuses());
			return "admin/schedule-form";
		}

		try {
			scheduleService.createSchedule(dto);
			redirectAttributes.addFlashAttribute("success", "Schedule added successfully!");
		} catch (Exception e) {
			log.error("Error saving schedule", e);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/admin/schedules";
	}

	@GetMapping("/schedules/delete/{id}")
	public String deleteSchedule(@PathVariable Long id,
			RedirectAttributes redirectAttributes) {

		try {
			scheduleService.deleteSchedule(id);
			redirectAttributes.addFlashAttribute("success", "Schedule deleted successfully");
		} catch (Exception e) {
			log.error("Error deleting schedule", e);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/admin/schedules";
	}

	// ========== BOOKING MANAGEMENT ==========

	@GetMapping("/bookings")
	public String listBookings(Model model) {
	    List<Booking> bookings = bookingService.getAllBookings();
	    
	    
	    long confirmedCount = bookings.stream()
	            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
	            .count();
	    
	    
	    long cancelledCount = bookings.stream()
	            .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
	            .count();
	    
	    
	    double totalRevenue = bookings.stream()
	            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
	            .mapToDouble(Booking::getTotalFare)
	            .sum();
	    
	    model.addAttribute("bookings", bookings);
	    model.addAttribute("confirmedCount", confirmedCount);
	    model.addAttribute("cancelledCount", cancelledCount);
	    model.addAttribute("totalRevenue", totalRevenue);
	    model.addAttribute("BookingStatus", BookingStatus.class);
	    
	    return "admin/bookings";
	}

}
