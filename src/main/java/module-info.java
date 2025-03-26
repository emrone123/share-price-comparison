/**
 * CLEAN ARCHITECTURE PRINCIPLE: Modularity & Component Boundaries
 * This module descriptor clearly defines the application boundaries and dependencies,
 * demonstrating clean separation of components and explicit dependency declaration.
 */
module com.stockmonitor {
    // CLEAN ARCHITECTURE PRINCIPLE: Dependency Rule
    // External framework dependencies are explicitly required here,
    // keeping the core domain independent from these details
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    
    // CLEAN ARCHITECTURE PRINCIPLE: Dependency Inversion & Interface Segregation
    // The exports indicate that we have well-defined interfaces and components
    // that can be consumed by other modules, maintaining clean boundaries
    exports com.stockmonitor;
    exports com.stockmonitor.domain;  // Domain entities are exported
    exports com.stockmonitor.interfaces;  // Interfaces defining contracts are exported
    exports com.stockmonitor.app;  // Application layer is exported
    exports com.stockmonitor.monitor;  // Business logic implementations are exported
    exports com.stockmonitor.database;  // Data access implementations are exported
    exports com.stockmonitor.frameworks.service;
    exports com.stockmonitor.interfaces.service;
    
    // CLEAN ARCHITECTURE PRINCIPLE: Framework Independence
    // These declarations isolate framework dependencies to specific packages,
    // preventing them from leaking into the domain layer
    opens com.stockmonitor.app to javafx.graphics, javafx.fxml;
    opens com.example.demo3 to javafx.graphics, javafx.fxml;
}