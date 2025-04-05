package com.stockmonitor.display;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.IChartDisplay;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Chart display implementation to visualize stock data
 * 
 * This version is adapted for web display (no JavaFX dependencies)
 * Implements the Adapter Pattern and serves as part of MVC architecture
 */
public class ChartDisplay implements IChartDisplay {
    
    private List<StockPrice> currentData = new ArrayList<>();
    private Map<String, List<StockPrice>> comparisonData = new HashMap<>();
    
    /**
     * Basic display chart implementation
     */
    @Override
    public void displayChart() {
        System.out.println("Displaying generic chart");
        // In web application, this would be handled by the view template
    }
    
    /**
     * Display stock data for a single stock
     * 
     * @param stockPrices List of stock prices to display
     */
    @Override
    public void displayStockData(List<StockPrice> stockPrices) {
        this.currentData = new ArrayList<>(stockPrices);
        System.out.println("Preparing to display " + stockPrices.size() + " data points");
        
        // In web application, the controller will pass this data to the view
    }
    
    /**
     * Compare two stocks
     * 
     * @param stockPrices1 First stock's price data
     * @param stockPrices2 Second stock's price data
     */
    @Override
    public void compareStocks(List<StockPrice> stockPrices1, List<StockPrice> stockPrices2) {
        if (stockPrices1.isEmpty() || stockPrices2.isEmpty()) {
            System.out.println("Cannot display comparison chart: missing data");
            return;
        }
        
        String symbol1 = stockPrices1.get(0).getSymbol();
        String symbol2 = stockPrices2.get(0).getSymbol();
        
        this.comparisonData.clear();
        this.comparisonData.put(symbol1, new ArrayList<>(stockPrices1));
        this.comparisonData.put(symbol2, new ArrayList<>(stockPrices2));
        
        System.out.println("Preparing to display comparison between " + symbol1 + " and " + symbol2);
        // In web application, the controller will pass this data to the view
    }
    
    /**
     * Get the currently displayed data
     * 
     * @return Current stock price data
     */
    public List<StockPrice> getCurrentData() {
        return this.currentData;
    }
    
    /**
     * Get the comparison data
     * 
     * @return Map of symbol to stock price data
     */
    public Map<String, List<StockPrice>> getComparisonData() {
        return this.comparisonData;
    }
} 