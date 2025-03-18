package com.stockmonitor.interfaces;

/**
 * Logging interface
 * Handles system logging
 */
public interface ILoggingData {
    /**
     * Records system events and messages
     * @param message The message to log
     */
    void log(String message);
} 