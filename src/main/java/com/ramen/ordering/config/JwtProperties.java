package com.ramen.ordering.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    // getters & setters
    private String secret;
    private long expiry;

}