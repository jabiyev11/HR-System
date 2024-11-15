package com.example.hr_system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class JwtConfig {

    private String secretKey;

    private Long expiration;
}
