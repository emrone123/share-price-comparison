package com.stockmonitor.controller;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Web MVC Controller for stock views
 * 
 * This follows:
 * - MVC Pattern: Controller part of Model-View-Controller
 * - Adapter Pattern: Adapts HTTP requests to service calls
 * - Layered Architecture: Controller layer that communicates with service layer
 */
@Controller
public class StockViewController {
    
    private final StockDataService stockDataService;
    
    @Autowired
    public StockViewController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }
    
    /**
     * Home page
     */
    @GetMapping("/")
    public String home(Model model) {
        // Add some popular stocks for quick selection
        model.addAttribute("popularStocks", List.of("AAPL", "MSFT", "GOOGL", "AMZN"));
        
        // Default date range
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        return "index";
    }
    
    /**
     * Compare stocks page
     */
    @GetMapping("/compare")
    public String compareStocks(
            @RequestParam(required = false) String symbol1,
            @RequestParam(required = false) String symbol2,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            Model model) {
        
        // Set default values if not provided
        symbol1 = (symbol1 != null && !symbol1.isEmpty()) ? symbol1 : "AAPL";
        symbol2 = (symbol2 != null && !symbol2.isEmpty()) ? symbol2 : "MSFT";
        endDate = (endDate != null) ? endDate : LocalDate.now();
        startDate = (startDate != null) ? startDate : endDate.minusMonths(1);
        
        // Get stock data
        List<StockPrice> stockData1 = stockDataService.fetchStockData(symbol1, startDate, endDate);
        List<StockPrice> stockData2 = stockDataService.fetchStockData(symbol2, startDate, endDate);
        
        // Add data to model
        model.addAttribute("symbol1", symbol1);
        model.addAttribute("symbol2", symbol2);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("stockData1", stockData1);
        model.addAttribute("stockData2", stockData2);
        
        // Add some popular stocks for quick selection
        model.addAttribute("popularStocks", List.of("AAPL", "MSFT", "GOOGL", "AMZN"));
        
        return "compare";
    }
    
    /**
     * Submit form for stock comparison
     */
    @PostMapping("/compare")
    public String submitCompareForm(
            @RequestParam String symbol1,
            @RequestParam String symbol2,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        
        // Redirect to GET endpoint with parameters
        return "redirect:/compare?symbol1=" + symbol1 + 
               "&symbol2=" + symbol2 + 
               "&startDate=" + startDate + 
               "&endDate=" + endDate;
    }
} 