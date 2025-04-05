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
 * Stock Comparison Application
 * 
 * This class has been converted from a JavaFX application to a service class
 * that provides stock comparison functionality for the web application.
 * 
 * It follows:
 * - Service-Oriented Architecture: Provides a well-defined service
 * - Adapter Pattern: Adapts the stock data service to the application needs
 */
public class StockComparisonApp {
    private final IStockDataService stockService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructor with stock service
     * 
     * @param stockService The stock data service to use
     */
    public StockComparisonApp(IStockDataService stockService) {
        this.stockService = stockService;
    }

    /**
     * Compare two stocks
     * 
     * @param symbol1 First stock symbol
     * @param symbol2 Second stock symbol
     * @param startDate Start date
     * @param endDate End date
     * @return Map containing comparison data
     */
    public Map<String, Object> compareStocks(String symbol1, String symbol2, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            startDate = LocalDate.now().minusMonths(1);
            endDate = LocalDate.now();
        }
        
        // Fetch real data for both stocks
        List<StockPrice> stockData1 = stockService.fetchStockData(symbol1, startDate, endDate);
        List<StockPrice> stockData2 = stockService.fetchStockData(symbol2, startDate, endDate);
        
        Map<String, Object> result = new HashMap<>();
        result.put("symbol1", symbol1);
        result.put("symbol2", symbol2);
        result.put("startDate", startDate);
        result.put("endDate", endDate);
        result.put("stockData1", stockData1);
        result.put("stockData2", stockData2);
        
        return result;
    }

    /**
     * Get data for a single stock
     * 
     * @param symbol Stock symbol
     * @param startDate Start date
     * @param endDate End date
     * @return Map containing stock data
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
        
        return result;
    }

    /**
     * Extract dates from stock price data for charting
     * 
     * @param stockPrices List of stock prices
     * @return List of formatted date strings
     */
    public List<String> extractDates(List<StockPrice> stockPrices) {
        List<String> dates = new ArrayList<>();
        for (StockPrice price : stockPrices) {
            dates.add(price.getDate().format(DATE_FORMATTER));
        }
        return dates;
    }

    /**
     * Extract prices from stock price data for charting
     * 
     * @param stockPrices List of stock prices
     * @return List of prices
     */
    public List<Double> extractPrices(List<StockPrice> stockPrices) {
        List<Double> prices = new ArrayList<>();
        for (StockPrice price : stockPrices) {
            prices.add(price.getPrice());
        }
        return prices;
    }
} 