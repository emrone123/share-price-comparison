package com.stockmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Spring Boot application class
 * 
 * This application follows multiple architectural styles:
 * 1. Layered Architecture: Separates concerns into layers (controllers, services, repositories)
 * 2. Model-View-Controller (MVC): Separates UI, business logic, and data access
 * 3. Service-Oriented Architecture (SOA): Uses modular services with well-defined interfaces
 * 4. N-Tier Architecture: Separates presentation, business logic, and data tiers
 */
@SpringBootApplication
@EnableScheduling
@EntityScan("com.stockmonitor.domain")
@EnableJpaRepositories("com.stockmonitor.repository")
public class StockMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockMonitorApplication.class, args);
    }
} 