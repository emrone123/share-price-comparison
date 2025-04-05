package com.stockmonitor.app;

import com.stockmonitor.core.StockApplication;
import com.stockmonitor.collectors.DataCollector;
import com.stockmonitor.database.StockDatabase;
import com.stockmonitor.display.ChartDisplay;
import com.stockmonitor.logging.ConsoleLogger;
import com.stockmonitor.interfaces.ILoggingData;
import com.stockmonitor.interfaces.IDataBase;
import com.stockmonitor.interfaces.IDataCollector;
import com.stockmonitor.interfaces.IChartDisplay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Main configuration class for the application
 * 
 * This class replaces the original JavaFX application with a Spring configuration
 * class that sets up the application components.
 * 
 * It follows:
 * - Dependency Injection: Components are created and wired together
 * - Factory Pattern: Creates and configures application components
 * - Separation of Concerns: Each bean has a single responsibility
 */
@Configuration
public class Main {
    
    /**
     * Create logger bean
     * 
     * @return Logger implementation
     */
    @Bean
    public ILoggingData logger() {
        return new ConsoleLogger();
    }
    
    /**
     * Create database bean
     * 
     * @return Database implementation
     */
    @Bean
    public IDataBase database() {
        return new StockDatabase();
    }
    
    /**
     * Create data collector bean
     * 
     * @return Data collector implementation
     */
    @Bean
    public IDataCollector dataCollector() {
        return new DataCollector();
    }
    
    /**
     * Create chart display bean
     * 
     * @return Chart display implementation
     */
    @Bean
    public IChartDisplay chartDisplay() {
        return new ChartDisplay();
    }
    
    /**
     * Create stock application bean
     * 
     * @param dataCollector Data collector to use
     * @param database Database to use
     * @param logger Logger to use
     * @return Configured stock application
     */
    @Bean
    public StockApplication stockApplication(
            IDataCollector dataCollector,
            IDataBase database,
            ILoggingData logger) {
        return new StockApplication(dataCollector, database, logger);
    }
} 