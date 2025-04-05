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
 * REST API Controller for stock price data
 * 
 * This follows:
 * - MVC Pattern: Controller part of Model-View-Controller
 * - Adapter Pattern: Adapts HTTP requests to service calls
 * - SOA: Exposes service functionalities as REST APIs
 */
@RestController
@RequestMapping("/api/stocks")
public class StockApiController {
    
    private final StockDataService stockDataService;
    
    @Autowired
    public StockApiController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }
    
    /**
     * Get stock data for a given symbol and date range
     * 
     * @param symbol Stock symbol
     * @param startDate Start date
     * @param endDate End date
     * @return List of stock prices
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
} 