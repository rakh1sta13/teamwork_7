package com.ramen.ordering;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Ramen Restaurant Ordering Platform API",
                version = "1.0",
                description = "API documentation for the Ramen Restaurant Ordering Platform",
                contact = @Contact(
                        name = "Ramen Platform Support",
                        email = "support@ramen-platform.com",
                        url = "https://ramen-platform.com/contact"
                )
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        },
        servers = {
                @Server(url = "https://api.ramen.uz", description = "Production server"),
                @Server(url = "https://api-dev.ramen.uz", description = "Development server"),
                @Server(url = "http://localhost:8080", description = "Local dev server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@SpringBootApplication
public class RamenOrderingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(RamenOrderingPlatformApplication.class, args);
    }

}