package com.stockmonitor.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Interface for monitoring stock prices as shown in the diagram
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Interface Segregation
 * This interface defines a specific set of related operations for stock monitoring,
 * avoiding "fat" interfaces with too many unrelated methods.
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Dependency Inversion
 * High-level modules (like the UI) depend on this abstraction rather than concrete implementations,
 * allowing for easy substitution of different stock monitoring strategies.
 */
public interface IStockMonitor {
    /**
     * Add a stock to the watchlist
     * @param symbol The stock symbol to add
     */
    void addStockToWatchlist(String symbol);
    
    /**
     * Remove a stock from the watchlist
     * @param symbol The stock symbol to remove
     */
    void removeStockFromWatchlist(String symbol);
    
    /**
     * Set the refresh interval for stock data
     * @param seconds The interval in seconds
     */
    void setRefreshInterval(int seconds);
    
    /**
     * Get the most recent stock quote
     * @return A map of stock symbols to their quote data
     */
    Map<String, Object> getStockQuote();
    
    /**
     * Get the current price of a specific stock
     * @return The current price
     */
    double getCurrentPrice();
    
    /**
     * Get price statistics for a specific stock
     * @param symbol The stock symbol
     * @return Statistics as a map of property names to values
     */
    Map<String, Object> getPriceStatistics(String symbol);
    
    /**
     * Start monitoring stocks in the watchlist at the set refresh interval
     */
    void startMonitoring();
    
    /**
     * Stop monitoring stocks
     */
    void stopMonitoring();
} 