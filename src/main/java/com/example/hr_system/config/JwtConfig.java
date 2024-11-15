package com.example.hr_system.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class JwtConfig {

//    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
//    @Value("${application.security.jwt.expiration}")
    private Long jwtExpiration;
}
