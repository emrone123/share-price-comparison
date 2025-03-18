package com.stockmonitor.interfaces;

/**
 * Data collector interface
 * Handles data collection from external sources
 */
public interface IDataCollector {
    /**
     * Retrieves stock data from external APIs
     */
    void collectData();
} 