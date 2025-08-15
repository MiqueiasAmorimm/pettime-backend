package com.pettime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Libera H2 Console e endpoints de teste
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().permitAll() // para teste, todos endpoints liberados
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // necessário para H2
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // necessário para H2
                );

        return http.build();
    }
}
