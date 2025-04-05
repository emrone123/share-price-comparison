package com.stockmonitor.controller;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Web controller for stock comparison views
 * 
 * SOA PRINCIPLE: Service Abstraction and Encapsulation
 * This controller abstracts the web presentation layer from the service layer,
 * providing a clean separation of concerns between presentation and business logic.
 * 
 * SOA PRINCIPLE: Service Composability
 * The controller composes with the StockDataService to create a higher-level
 * service capability for the web interface.
 */
@Controller
public class StockViewController {

    private final StockDataService stockDataService;

    /**
     * SOA PRINCIPLE: Service Loose Coupling
     * Dependency injection enables loose coupling between this controller and
     * the service implementation, allowing for different implementations to be
     * substituted without changing the controller code.
     */
    @Autowired
    public StockViewController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    /**
     * SOA PRINCIPLE: Service Contract
     * This endpoint provides a clear contract for accessing the home page view.
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * SOA PRINCIPLE: Service Interoperability and Reusability
     * This controller method integrates with the service layer and transforms
     * the data into a format suitable for the view layer, providing a reusable
     * capability accessible via standard web protocols.
     * 
     * SOA PRINCIPLE: Service Orchestration
     * This method orchestrates multiple service calls to provide a cohesive view
     * for stock price comparison.
     */
    @PostMapping("/compare")
    public String compareStocks(
            @RequestParam String symbol1,
            @RequestParam String symbol2,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        
        List<StockPrice> stockData1 = stockDataService.fetchStockData(symbol1, startDate, endDate);
        List<StockPrice> stockData2 = stockDataService.fetchStockData(symbol2, startDate, endDate);
        
        model.addAttribute("symbol1", symbol1);
        model.addAttribute("symbol2", symbol2);
        model.addAttribute("stockData1", stockData1);
        model.addAttribute("stockData2", stockData2);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        return "compare";
    }

    /**
     * SOA PRINCIPLE: Service Discoverability
     * This endpoint has a clear, intuitive URL path that makes it easily
     * discoverable for clients that need stock comparison functionality.
     */
    @GetMapping("/compare")
    public String compareStocksGet(
            @RequestParam String symbol1,
            @RequestParam(required = false) String symbol2,
            Model model) {
        
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);
        
        // Default to MSFT if symbol2 is not provided
        if (symbol2 == null || symbol2.isEmpty()) {
            symbol2 = "MSFT";
        }
        
        return compareStocks(symbol1, symbol2, startDate, endDate, model);
    }
} 