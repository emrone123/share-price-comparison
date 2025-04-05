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
 * 
 * SOA PRINCIPLES IMPLEMENTATION:
 * 
 * 1. Service Contract: Well-defined interfaces in the service layer that establish
 *    clear contracts between service providers and consumers.
 *    Example: StockDataService interface defines a contract for stock data operations.
 * 
 * 2. Service Abstraction: Services hide their internal implementations and expose
 *    only what's necessary through interfaces.
 *    Example: YahooFinanceStockDataService implements StockDataService, hiding
 *    implementation details from consumers.
 * 
 * 3. Service Autonomy: Services operate independently with clear boundaries.
 *    Example: Each service manages its own state and logic.
 * 
 * 4. Service Reusability: Services are designed to be reused across different parts
 *    of the application or by external clients.
 *    Example: StockDataService can be used by multiple controllers and components.
 * 
 * 5. Service Composability: Services can be composed together to create new functionality.
 *    Example: Controllers compose with services through dependency injection.
 * 
 * 6. Service Statelessness: Services do not maintain client state between requests.
 *    Example: RESTful endpoints are stateless, with all needed context passed in requests.
 * 
 * 7. Service Discoverability: Services and their capabilities are easily discoverable.
 *    Example: Service methods have clear names and well-defined contracts.
 * 
 * 8. Service Interoperability: Services can be consumed by various clients
 *    regardless of platform or technology.
 *    Example: REST API endpoints can be consumed by any HTTP-capable client.
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