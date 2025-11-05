package com.pettime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 🛡️ Production security configuration.
 * Configuration de sécurité pour l'environnement de production.
 */
@Configuration
@Profile("!dev")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ✅ Enable CSRF protection for production
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // still allow local console if needed
                )

                // ✅ Allow only specific public endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        .anyRequest().authenticated()
                )

                // ✅ Secure headers and same-origin for H2
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                // ✅ Enforce HTTP Basic (can later be replaced by JWT or Firebase Auth)
                .httpBasic(httpBasic -> {});

        return http.build();
    }
}
