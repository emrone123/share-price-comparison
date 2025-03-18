module com.stockmonitor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    
    exports com.stockmonitor.app;
    exports com.stockmonitor.core;
    exports com.stockmonitor.interfaces;
    exports com.stockmonitor.abstracts;
    exports com.stockmonitor.collectors;
    exports com.stockmonitor.database;
    exports com.stockmonitor.display;
    exports com.stockmonitor.logging;
    exports com.stockmonitor.monitoring;
    exports com.stockmonitor.pricing;
}