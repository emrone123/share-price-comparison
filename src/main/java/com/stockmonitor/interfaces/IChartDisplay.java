package com.stockmonitor.interfaces;

import com.stockmonitor.domain.StockPrice;
import java.util.List;

/**
 * Interface for chart display components
 * 
 * This interface follows the Interface Segregation Principle by
 * providing specific methods for chart display operations.
 */
public interface IChartDisplay {
    /**
     * Display a general chart (basic operation)
     */
    void displayChart();
    
    /**
     * Display stock data for a single stock
     * 
     * @param stockPrices List of stock prices to display
     */
    void displayStockData(List<StockPrice> stockPrices);
    
    /**
     * Compare two stocks
     * 
     * @param stockPrices1 First stock's price data
     * @param stockPrices2 Second stock's price data
     */
    void compareStocks(List<StockPrice> stockPrices1, List<StockPrice> stockPrices2);
}
