package com.stockmonitor.service;

import com.stockmonitor.domain.StockPrice;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for stock data operations
 * 
 * SOA PRINCIPLE: Service Contract
 * This interface defines a clear contract for stock data services,
 * specifying the available operations without exposing implementation details.
 * 
 * SOA PRINCIPLE: Service Abstraction
 * The interface abstracts the underlying stock data retrieval mechanisms,
 * hiding the complexity from service consumers.
 * 
 * SOA PRINCIPLE: Service Loose Coupling
 * By defining this interface, we ensure loose coupling between components,
 * as consumers depend on the abstraction rather than concrete implementations.
 */
public interface StockDataService {
    
    /**
     * SOA PRINCIPLE: Service Autonomy
     * Each service method represents a self-contained operation that can be
     * executed independently with clearly defined inputs and outputs.
     */
    List<StockPrice> fetchStockData(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * SOA PRINCIPLE: Service Reusability
     * These operations are designed to be reusable across different parts
     * of the application or by external clients.
     */
    StockPrice saveStockPrice(StockPrice stockPrice);
    
    /**
     * Save multiple stock prices in one operation
     * @param stockPrices List of stock prices to save
     * @return List of saved stock prices
     */
    List<StockPrice> saveAllStockPrices(List<StockPrice> stockPrices);
    
    /**
     * SOA PRINCIPLE: Service Discoverability
     * The clear method naming and purpose make this service easy to discover
     * and understand by potential consumers.
     */
    StockPrice getLatestStockPrice(String symbol);
} 