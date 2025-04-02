package com.utopia.designmyexperience_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // désactive la protection CSRF (utile pour tests API sans token)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // autorise l'accès à toutes les routes sans authentification
                )
                .httpBasic(withDefaults()); // active l'authentification de base (utile mais pas obligatoire)

        return http.build();
    }
}