package com.stockmonitor.interfaces;

import com.stockmonitor.domain.StockPrice;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface for storing and retrieving historical stock data as shown in the diagram
 */
public interface IDataStorage {
    /**
     * Save stock data to persistent storage
     * @param data The stock data to save
     */
    void saveStockData(StockPrice data);
    
    /**
     * Retrieve stored stock data for a symbol within a date range
     * @param symbol The stock symbol
     * @param startDate The start date
     * @return List of stock price data
     */
    List<StockPrice> retrieveStockData(String symbol, LocalDate startDate);
    
    /**
     * Archive data older than the specified date
     * @param olderThan The cutoff date
     */
    void archiveData(LocalDate olderThan);
    
    /**
     * Get statistics about the storage system
     * @return Map of statistics
     */
    Map<String, Object> getStorageStats();
} 