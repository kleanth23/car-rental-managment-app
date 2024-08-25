package com.car_rental_managment_app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for API endpoints
                .cors().and() // Enable CORS if needed globally
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/**", "/api/reservations/**"// Permit all requests under the API context
                        ).permitAll() // Permit all requests to these patterns
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .httpBasic(); // If using HTTP Basic authentication, otherwise configure as needed
        return http.build();
    }
}
