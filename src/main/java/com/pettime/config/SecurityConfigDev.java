package com.pettime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 🔧 Development security configuration.
 * Configuration de sécurité pour l'environnement de développement.
 */
@Configuration
@Profile("dev")
public class SecurityConfigDev {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //  Disable CSRF to simplify local testing (MockMvc, Postman, etc.)
                .csrf(AbstractHttpConfigurer::disable)

                //  Allow all routes during dev for faster iteration
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/**", "/h2-console/**").permitAll()
                        .anyRequest().permitAll()
                )

                // Allow H2 console frames
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // Simple HTTP Basic (optional for manual API testing)
                .httpBasic(httpBasic -> {});

        return http.build();
    }
}
