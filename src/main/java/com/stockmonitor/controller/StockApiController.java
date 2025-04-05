package com.stockmonitor.controller;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST API Controller for Stock Data
 * 
 * SOA PRINCIPLE: Service Contract
 * This controller exposes a well-defined API contract for accessing stock data
 * through standardized HTTP methods and endpoints.
 * 
 * SOA PRINCIPLE: Service Abstraction
 * The controller abstracts the implementation details and provides a simplified
 * interface for clients to interact with the stock data service.
 */
@RestController
@RequestMapping("/api/stocks")
public class StockApiController {
    
    private final StockDataService stockDataService;
    
    /**
     * SOA PRINCIPLE: Service Composability
     * The controller composes with the StockDataService through dependency injection,
     * allowing for flexible composition of different service implementations.
     */
    @Autowired
    public StockApiController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }
    
    /**
     * SOA PRINCIPLE: Service Interoperability
     * This RESTful endpoint provides a standard HTTP interface that can be
     * consumed by any client regardless of platform or programming language.
     * 
     * SOA PRINCIPLE: Service Statelessness
     * The endpoint is stateless, with all necessary information (symbol, startDate, endDate)
     * passed in each request, allowing for scalability and reliability.
     */
    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockPrice>> getStockData(
            @PathVariable String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<StockPrice> stockData = stockDataService.fetchStockData(symbol, startDate, endDate);
        return ResponseEntity.ok(stockData);
    }
    
    /**
     * Get the latest stock price for a given symbol
     * 
     * @param symbol Stock symbol
     * @return Latest stock price
     */
    @GetMapping("/{symbol}/latest")
    public ResponseEntity<StockPrice> getLatestStockPrice(@PathVariable String symbol) {
        StockPrice latestPrice = stockDataService.getLatestStockPrice(symbol);
        
        if (latestPrice == null) {
            // If no data exists yet, get today's data
            List<StockPrice> todayData = stockDataService.fetchStockData(
                    symbol, LocalDate.now().minusDays(5), LocalDate.now());
            
            if (!todayData.isEmpty()) {
                latestPrice = todayData.get(todayData.size() - 1);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        
        return ResponseEntity.ok(latestPrice);
    }
    
    /**
     * Compare two stocks over a date range
     * 
     * @param symbol1 First stock symbol
     * @param symbol2 Second stock symbol
     * @param startDate Start date
     * @param endDate End date
     * @return Map with both stock data lists
     */
    @GetMapping("/compare")
    public ResponseEntity<Object> compareStocks(
            @RequestParam String symbol1,
            @RequestParam String symbol2,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<StockPrice> stockData1 = stockDataService.fetchStockData(symbol1, startDate, endDate);
        List<StockPrice> stockData2 = stockDataService.fetchStockData(symbol2, startDate, endDate);
        
        return ResponseEntity.ok(new Object() {
            public final List<StockPrice> data1 = stockData1;
            public final List<StockPrice> data2 = stockData2;
        });
    }

    /**
     * SOA PRINCIPLE: Service Reusability
     * This API endpoint can be reused by multiple client applications,
     * providing a standard way to save stock price data.
     */
    @PostMapping
    public StockPrice saveStockPrice(@RequestBody StockPrice stockPrice) {
        return stockDataService.saveStockPrice(stockPrice);
    }
} 