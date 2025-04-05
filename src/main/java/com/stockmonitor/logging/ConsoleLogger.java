package com.stockmonitor.logging;

import com.stockmonitor.interfaces.ILoggingData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple console logger implementation
 * 
 * This class implements the Logger interface to provide
 * console-based logging functionality.
 */
public class ConsoleLogger implements ILoggingData {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println("[" + timestamp + "] " + message);
    }
    
    /**
     * Log an error message (extra utility method)
     * 
     * @param message Error message to log
     */
    public void logError(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.err.println("[" + timestamp + "] ERROR: " + message);
    }
} 