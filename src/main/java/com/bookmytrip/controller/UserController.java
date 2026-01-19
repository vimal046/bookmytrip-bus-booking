package com.bookmytrip.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookmytrip.dto.BookingRequestDTO;
import com.bookmytrip.dto.SearchBusDTO;
import com.bookmytrip.enums.BookingStatus;
import com.bookmytrip.model.Booking;
import com.bookmytrip.model.Schedule;
import com.bookmytrip.model.User;
import com.bookmytrip.service.BookingService;
import com.bookmytrip.service.BusService;
import com.bookmytrip.service.RouteService;
import com.bookmytrip.service.ScheduleService;
import com.bookmytrip.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * User Controller - User module endpoints
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final BookingService bookingService;
    private final RouteService routeService;
    private final BusService busService;
    
   //User dashboard with routes, buses, and schedules
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("searchBus", new SearchBusDTO());
        
        // Load all routes, buses, and today's schedules
        try {
            model.addAttribute("routes", routeService.getAllRoutes());
            model.addAttribute("buses", busService.getAllBuses());
            
            // Get today's and upcoming schedules
            List<Schedule> schedules = scheduleService.getAllSchedules().stream()
                    .filter(s -> !s.getDate().isBefore(LocalDate.now()))
                    .sorted((s1, s2) -> s1.getDate().compareTo(s2.getDate()))
                    .limit(10)
                    .toList();
            model.addAttribute("schedules", schedules);
        } catch (Exception e) {
            log.error("Error loading dashboard data", e);
        }
        
        return "user/dashboard";
    }
    
    //Search buses
    @PostMapping("/search")
    public String searchBuses(@Valid @ModelAttribute("searchBus") SearchBusDTO dto,
                              BindingResult result,
                              Model model,
                              @AuthenticationPrincipal UserDetails userDetails) {
        
        if (result.hasErrors()) {
            // Reload data on error
            User user = userService.getUserByEmail(userDetails.getUsername());
            model.addAttribute("user", user);
            model.addAttribute("routes", routeService.getAllRoutes());
            model.addAttribute("buses", busService.getAllBuses());
            model.addAttribute("schedules", scheduleService.getAllSchedules());
            return "user/dashboard";
        }
        
        List<Schedule> schedules = scheduleService.searchSchedules(
                dto.getSource(), dto.getDestination(), dto.getDate());
        
        model.addAttribute("schedules", schedules);
        model.addAttribute("searchBus", dto);
        return "user/search-results";
    }
    
    //Show booking form
    @GetMapping("/book/{scheduleId}")
    public String showBookingForm(@PathVariable Long scheduleId, Model model) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        model.addAttribute("schedule", schedule);
        
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setScheduleId(scheduleId);
        model.addAttribute("bookingRequest", bookingRequest);
        
        return "user/booking-form";
    }
    
    //Create booking
    @PostMapping("/book")
    public String createBooking(@Valid @ModelAttribute("bookingRequest") BookingRequestDTO dto,
                                BindingResult result,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        
        if (result.hasErrors()) {
            Schedule schedule = scheduleService.getScheduleById(dto.getScheduleId());
            model.addAttribute("schedule", schedule);
            return "user/booking-form";
        }
        
        try {
            User user = userService.getUserByEmail(userDetails.getUsername());
            Booking booking = bookingService.createBooking(dto, user.getId());
            redirectAttributes.addFlashAttribute("success", 
                    "Booking confirmed! Reference: " + booking.getBookingReference());
            return "redirect:/user/bookings";
        } catch (Exception e) {
            log.error("Booking error", e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/book/" + dto.getScheduleId();
        }
    }
    
    //View booking history
    @GetMapping("/bookings")
    public String viewBookings(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<Booking> bookings = bookingService.getBookingsByUser(user.getId());
        model.addAttribute("BookingStatus", BookingStatus.class);
        model.addAttribute("bookings", bookings);
        model.addAttribute("user", user);
        return "user/bookings";
    }
    
    //Cancel booking
    @PostMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        
        try {
            User user = userService.getUserByEmail(userDetails.getUsername());
            bookingService.cancelBooking(bookingId, user.getId());
            redirectAttributes.addFlashAttribute("success", "Booking cancelled successfully!");
        } catch (Exception e) {
            log.error("Cancellation error", e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/user/bookings";
    }
    
   ///View all routes (separate page)
    @GetMapping("/routes")
    public String viewRoutes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("routes", routeService.getAllRoutes());
        return "user/routes";
    }
    
    //View all buses (separate page)
    @GetMapping("/buses")
    public String viewBuses(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("buses", busService.getAllBuses());
        return "user/buses";
    }
    
    //View all schedules (separate page)
    @GetMapping("/schedules")
    public String viewSchedules(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        
        // Get upcoming schedules only
        List<Schedule> schedules = scheduleService.getAllSchedules().stream()
                .filter(s -> !s.getDate().isBefore(LocalDate.now()))
                .sorted((s1, s2) -> s1.getDate().compareTo(s2.getDate()))
                .toList();
        model.addAttribute("schedules", schedules);
        
        return "user/schedules";
    }
}