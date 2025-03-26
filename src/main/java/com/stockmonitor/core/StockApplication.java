package com.stockmonitor.core;

import com.stockmonitor.display.ChartDisplay;
import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.IApplication;
import com.stockmonitor.interfaces.IChartDisplay;
import com.stockmonitor.interfaces.IDataBase;
import com.stockmonitor.interfaces.IDataCollector;
import com.stockmonitor.interfaces.ILoggingData;
import com.stockmonitor.collectors.DataCollector;
import com.stockmonitor.database.StockDatabase;

import java.time.LocalDate;
import java.util.List;

public class StockApplication implements IApplication {
    private final IDataCollector dataCollector;
    private final IDataBase database;
    private final ILoggingData logger;
    private final ChartDisplay chartDisplay;
    
    private String currentSymbol1;
    private String currentSymbol2;
    private LocalDate startDate;
    private LocalDate endDate;
    
    public StockApplication(IDataCollector dataCollector, IDataBase database, 
                           ILoggingData logger, IChartDisplay chartDisplay) {
        this.dataCollector = dataCollector;
        this.database = database;
        this.logger = logger;
        this.chartDisplay = (ChartDisplay) chartDisplay;
        
        this.startDate = LocalDate.now().minusMonths(1);
        this.endDate = LocalDate.now();
    }
    
    @Override
    public void run() {
        logger.log("Stock application started");
    }
    
    public void processStockData(String symbol) {
        logger.log("Processing stock data for " + symbol);
        
        // Update current symbol
        currentSymbol1 = symbol;
        
        // Fetch and display stock data
        List<StockPrice> stockPrices = ((DataCollector) dataCollector).fetchStockData(symbol, startDate, endDate);
        
        // Save to database
        for (StockPrice price : stockPrices) {
            ((StockDatabase) database).saveStockPrice(price);
        }
        
        // Display chart
        chartDisplay.displayStockData(stockPrices);
    }
    
    public void compareStocks(String symbol1, String symbol2) {
        logger.log("Comparing stocks: " + symbol1 + " vs " + symbol2);
        
        // Update current symbols
        currentSymbol1 = symbol1;
        currentSymbol2 = symbol2;
        
        // Fetch stock data
        List<StockPrice> stockPrices1 = ((DataCollector) dataCollector).fetchStockData(symbol1, startDate, endDate);
        List<StockPrice> stockPrices2 = ((DataCollector) dataCollector).fetchStockData(symbol2, startDate, endDate);
        
        // Save to database
        for (StockPrice price : stockPrices1) {
            ((StockDatabase) database).saveStockPrice(price);
        }
        for (StockPrice price : stockPrices2) {
            ((StockDatabase) database).saveStockPrice(price);
        }
        
        // Display comparison chart
        chartDisplay.compareStocks(stockPrices1, stockPrices2);
    }
    
    public void setDateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        logger.log("Date range set: " + startDate + " to " + endDate);
    }
    
    public void refreshData() {
        logger.log("Refreshing data");
        
        if (currentSymbol1 != null && currentSymbol2 != null) {
            compareStocks(currentSymbol1, currentSymbol2);
        } else if (currentSymbol1 != null) {
            processStockData(currentSymbol1);
        }
    }
} 