package com.automatch.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataInitializer - Seeds sample car data into the database on application startup.
 * Disabled for now to speed up compilation.
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        // Sample car data initialization disabled
        // Database will be populated through REST API or manual scripts
        System.out.println("✓ DataInitializer ready");
    }
}
