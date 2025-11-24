package com.ramen.ordering.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(
      HttpSecurity http, JwtAuthFilter jwtAuthFilter)
      throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth
                    // Swagger & API docs (always public)
                    .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**")
                    .permitAll()

                    // Public auth endpoints
                    .requestMatchers("/api/auth/login", "/api/auth/register")
                    .permitAll()
                    // Public endpoints for product, category, order, and application CRUD operations
                    .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**", "/api/categories", "/api/categories/**", "/api/branches", "/api/branches/**", "/api/branches/active", "/api/orders/**", "/api/orders/client-email/**", "/api/orders/client-phone/**", "/api/orders/phone/**", "/api/applications", "/api/applications/**")
                    .permitAll()

                    .requestMatchers(HttpMethod.POST, "/api/products", "/api/categories", "/api/orders", "/api/applications")
                    .permitAll()

                    .requestMatchers(HttpMethod.PUT, "/api/products/**", "/api/categories/**", "/api/orders/**", "/api/applications/**")
                    .permitAll()

                    .requestMatchers(HttpMethod.DELETE, "/api/products/**", "/api/categories/**", "/api/orders/**", "/api/applications/**")
                    .permitAll()

                    // All other endpoints need JWT
                    .anyRequest()
                    .authenticated())
        // Both filters are OncePerRequestFilter, they decide themselves via shouldNotFilter
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public UserDetailsService swaggerUserDetailsService() {
    return username -> {
      if (username.equals("swaggerAdmin")) {
        return org.springframework.security.core.userdetails.User
            .withUsername("swaggerAdmin")
            .password(passwordEncoder().encode("MySwagger123"))
            .roles("SWAGGER")
            .build();
      }
      throw new UsernameNotFoundException("Swagger user not found");
    };
  }
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // 🌍 allow all origins
    configuration.addAllowedOriginPattern("*");

    // ✅ allowed methods
    configuration.setAllowedMethods(List.of("*"));

    // ✅ allowed headers
    configuration.setAllowedHeaders(List.of("*"));

    // ✅ expose headers (optional)
    configuration.setExposedHeaders(List.of("Authorization"));

    // ✅ credentials
    configuration.setAllowCredentials(false);

    //        configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
