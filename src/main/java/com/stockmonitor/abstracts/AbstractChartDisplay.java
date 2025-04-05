package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IChartDisplay;
import com.stockmonitor.domain.StockPrice;
import java.util.List;

/**
 * Abstract chart display base class
 * 
 * Implements shared functionality and declares abstract methods
 * for specific chart implementations to complete.
 */
public abstract class AbstractChartDisplay implements IChartDisplay {
    protected String chartType;

    public AbstractChartDisplay(String chartType) {
        this.chartType = chartType;
    }

    /**
     * Display a generic chart (to be implemented by subclasses)
     */
    @Override
    public abstract void displayChart();
    
    /**
     * Display stock data for a single stock
     * (to be implemented by subclasses)
     * 
     * @param stockPrices List of stock prices to display
     */
    @Override
    public abstract void displayStockData(List<StockPrice> stockPrices);
    
    /**
     * Compare two stocks
     * (to be implemented by subclasses)
     * 
     * @param stockPrices1 First stock's price data
     * @param stockPrices2 Second stock's price data
     */
    @Override
    public abstract void compareStocks(List<StockPrice> stockPrices1, List<StockPrice> stockPrices2);
}
