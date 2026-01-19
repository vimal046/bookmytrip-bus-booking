package com.bookmytrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(auth->auth
		//public endpoints
		.requestMatchers("/","/register","/login","/css/**","/js/**","/images/**").permitAll()
		
		//Admin endpoints
		.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
		
		//User endpoints
		.requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
		
		//All other requests required authenticated
		.anyRequest().authenticated()
				)
          
		.formLogin(form->form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/dashboard",true)
				.failureUrl("/login?error=true")
				.permitAll()
				)
		.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
				)
		.exceptionHandling(exception->exception
				.accessDeniedPage("/error/403")
				)
		.userDetailsService(userDetailsService);
		
		return http.build();
	}

    /*Password encoder bean*/
    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
