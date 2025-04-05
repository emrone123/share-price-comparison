package com.stockmonitor.service;

import com.stockmonitor.domain.StockPrice;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for stock data operations
 * 
 * This follows the SOA principles by defining a clear service contract.
 * Part of the service layer in the N-tier architecture.
 * Also implements the Interface Segregation Principle from SOLID.
 */
public interface StockDataService {
    
    /**
     * Fetch stock data for a given symbol and date range
     * @param symbol Stock symbol
     * @param startDate Start date
     * @param endDate End date
     * @return List of stock prices
     */
    List<StockPrice> fetchStockData(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * Save stock price data
     * @param stockPrice Stock price to save
     * @return Saved stock price
     */
    StockPrice saveStockPrice(StockPrice stockPrice);
    
    /**
     * Save multiple stock prices in one operation
     * @param stockPrices List of stock prices to save
     * @return List of saved stock prices
     */
    List<StockPrice> saveAllStockPrices(List<StockPrice> stockPrices);
    
    /**
     * Get the latest stock price for a symbol
     * @param symbol Stock symbol
     * @return Latest stock price
     */
    StockPrice getLatestStockPrice(String symbol);
} 