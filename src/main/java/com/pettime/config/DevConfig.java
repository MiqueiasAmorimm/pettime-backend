package com.pettime.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    private static final Logger logger = LoggerFactory.getLogger(DevConfig.class);

    @Bean
    public CommandLineRunner initDevEnvironment() {
        return args -> {
            logger.info("🚀 Development profile active — loading dev configuration...");
            // Example: initialize H2 data or seed test entities
        };
    }
}
