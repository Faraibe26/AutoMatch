package com.automatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

/**
 * AutoMatch AI - Main Spring Boot Application
 * 
 * AI-powered car recommendation system using Spring Boot, PostgreSQL, React, and JWT Authentication.
 * 
 * Refactored from the Banking Management System to serve as a car recommendation platform.
 * Provides REST APIs for:
 * - User authentication (JWT)
 * - Car catalog browsing and filtering
 * - AI-powered recommendations
 * - Favorite car management
 * 
 * Stack:
 * - Backend: Spring Boot 2.7.14, PostgreSQL/H2
 * - Authentication: JWT (JSON Web Tokens)
 * - Frontend: React.js (separate repo)
 * - UI: TailwindCSS
 */
@SpringBootApplication
public class AutoMatchAiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AutoMatchAiApplication.class, args);
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     AutoMatch AI - API Server Ready    ║");
        System.out.println("║                                        ║");
        System.out.println("║  Server: http://localhost:8080/api    ║");
        System.out.println("║  Swagger: http://localhost:8080/api/swagger-ui.html ║");
        System.out.println("║  H2 DB: http://localhost:8080/api/h2-console ║");
        System.out.println("║                                        ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\n");
    }
    
    /**
     * CORS filter configuration for frontend communication.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
