package com.pettime.config;  // Note o pacote config

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")  // Prefixo customizado (opcional)
@Getter
@Setter
public class AppProperties {

    // Firebase
    private String firebaseConfigPath;
    private String firebaseDatabaseUrl;

    // Stripe
    private String stripeSecretKey;
    private String stripePublicKey;

    // H2 (se necessário)
    private boolean h2ConsoleEnabled;
}