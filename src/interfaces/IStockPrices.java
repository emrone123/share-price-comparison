package com.stockmonitor.interfaces;

/**
 * Stock prices interface
 * Manages stock price information
 */
public interface IStockPrices {
    /**
     * Updates the current price of a stock
     * @param price The new price value
     */
    void updateStockPrice(double price);
    
    /**
     * Retrieves the current price of a stock
     * @return The current stock price
     */
    double getCurrentPrice();
} 