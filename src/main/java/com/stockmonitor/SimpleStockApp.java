package com.stockmonitor;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.service.IStockDataService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple Stock Application
 * 
 * This class has been converted from a JavaFX application to a service class
 * that provides basic stock data functionality for the web application.
 * 
 * It follows:
 * - Service-Oriented Architecture: Provides a focused, single-responsibility service
 * - Adapter Pattern: Adapts the stock data service to the application needs
 */
public class SimpleStockApp {
    private final IStockDataService stockService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructor with stock service
     * 
     * @param stockService The stock data service to use
     */
    public SimpleStockApp(IStockDataService stockService) {
        this.stockService = stockService;
    }

    /**
     * Get stock data for a single symbol
     * 
     * @param symbol Stock symbol
     * @param startDate Start date
     * @param endDate End date
     * @return Map containing stock data and metadata
     */
    public Map<String, Object> getStockData(String symbol, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            startDate = LocalDate.now().minusMonths(1);
            endDate = LocalDate.now();
        }
        
        List<StockPrice> stockData = stockService.fetchStockData(symbol, startDate, endDate);
        
        Map<String, Object> result = new HashMap<>();
        result.put("symbol", symbol);
        result.put("startDate", startDate);
        result.put("endDate", endDate);
        result.put("stockData", stockData);
        
        // Calculate some simple analytics
        if (!stockData.isEmpty()) {
            double minPrice = Double.MAX_VALUE;
            double maxPrice = Double.MIN_VALUE;
            double sum = 0;
            
            for (StockPrice price : stockData) {
                double p = price.getPrice();
                minPrice = Math.min(minPrice, p);
                maxPrice = Math.max(maxPrice, p);
                sum += p;
            }
            
            double avgPrice = sum / stockData.size();
            
            result.put("minPrice", minPrice);
            result.put("maxPrice", maxPrice);
            result.put("avgPrice", avgPrice);
            result.put("priceRange", maxPrice - minPrice);
        }
        
        return result;
    }

    /**
     * Get a list of common stock symbols
     * 
     * @return List of common stock symbols
     */
    public List<String> getCommonStocks() {
        return List.of("AAPL", "MSFT", "GOOGL", "AMZN", "META", "TSLA", "NVDA", "JPM");
    }

    /**
     * Extract data for charting
     * 
     * @param stockPrices List of stock prices
     * @return Map containing dates and prices
     */
    public Map<String, List<?>> extractChartData(List<StockPrice> stockPrices) {
        List<String> dates = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        
        for (StockPrice price : stockPrices) {
            dates.add(price.getDate().format(DATE_FORMATTER));
            prices.add(price.getPrice());
        }
        
        Map<String, List<?>> chartData = new HashMap<>();
        chartData.put("dates", dates);
        chartData.put("prices", prices);
        
        return chartData;
    }
} 