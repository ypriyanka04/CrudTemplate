package com.myapp.crudtemplate.config;

import com.myapp.crudtemplate.service.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables method-level security (if needed)
public class SecurityConfig {

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PersonService personService, PasswordEncoder passwordEncoder) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer :: disable) // Disable CSRF for simplicity; consider enabling in production
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/persons/**").permitAll() // Adjust as needed
                        .anyRequest().authenticated() // Other requests require authentication
                );
        return http.build();
    }

}
