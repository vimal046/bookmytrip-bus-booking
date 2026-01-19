package com.bookmytrip.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookmytrip.dto.UserRegistrationDTO;
import com.bookmytrip.service.BusService;
import com.bookmytrip.service.RouteService;
import com.bookmytrip.service.ScheduleService;
import com.bookmytrip.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Home Controller - Public pages
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    
    private final UserService userService;
    private final RouteService routeService;
    private final BusService busService;
    private final ScheduleService scheduleService;
    
   
     // Home page with routes, buses, and schedules preview
    @GetMapping("/")
    public String home(Model model) {
        // Get sample data for public view (limit to 6 items for preview)
        try {
            model.addAttribute("routes", routeService.getAllRoutes().stream().limit(6).toList());
            model.addAttribute("buses", busService.getAllBuses().stream().limit(4).toList());
            model.addAttribute("schedules", scheduleService.getAllSchedules().stream().limit(5).toList());
        } catch (Exception e) {
            log.warn("Could not load preview data: {}", e.getMessage());
        }
        return "common/home";
    }
    
   
     // Login page
    @GetMapping("/login")
    public String login() {
        return "common/login";
    }
    
    
     // Registration page 
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "common/register";
    }
    
    //Handle registration
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO dto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "common/register";
        }
        
        try {
            userService.registerUser(dto);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            log.error("Registration error", e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
    
    //Dashboard - Redirect based on role
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/user/dashboard";
    }
}