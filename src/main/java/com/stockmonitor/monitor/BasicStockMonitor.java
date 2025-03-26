package com.stockmonitor.monitor;

import com.stockmonitor.interfaces.IStockMonitor;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Basic implementation of the IStockMonitor interface
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Use Case Implementation
 * This class implements the business rules specified by the IStockMonitor interface,
 * representing the application-specific business rules layer in Clean Architecture.
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Independence from External Details
 * This implementation is independent from UI and database details, focusing only on
 * the business logic of stock monitoring.
 */
public class BasicStockMonitor implements IStockMonitor {
    // CLEAN ARCHITECTURE PRINCIPLE: Encapsulation
    // Internal implementation details are hidden from outer layers
    private final Map<String, Double> watchlist;
    private int refreshInterval;
    private ScheduledExecutorService scheduler;
    private boolean isRunning;
    private double currentPrice;
    
    public BasicStockMonitor() {
        this.watchlist = new HashMap<>();
        this.refreshInterval = 60; // Default to 60 seconds
        this.isRunning = false;
        this.currentPrice = 0.0;
    }
    
    /**
     * CLEAN ARCHITECTURE PRINCIPLE: Business Rule Implementation
     * This method implements a core business rule (adding stocks to watchlist)
     * as defined in the interface.
     */
    @Override
    public void addStockToWatchlist(String symbol) {
        if (!watchlist.containsKey(symbol)) {
            try {
                // Simulate getting a stock price - in a real implementation, this would connect to an API
                double price = generateRandomPrice();
                watchlist.put(symbol, price);
                currentPrice = price;
                System.out.println("Added " + symbol + " to watchlist at price: " + price);
            } catch (Exception e) {
                System.err.println("Error adding " + symbol + " to watchlist: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void removeStockFromWatchlist(String symbol) {
        if (watchlist.remove(symbol) != null) {
            System.out.println("Removed " + symbol + " from watchlist");
        } else {
            System.out.println(symbol + " was not in watchlist");
        }
    }
    
    /**
     * CLEAN ARCHITECTURE PRINCIPLE: Configuration Independent of Framework
     * The business rule (refresh interval) is implemented independently of how
     * the actual refresh mechanism works.
     */
    @Override
    public void setRefreshInterval(int seconds) {
        this.refreshInterval = seconds;
        if (isRunning) {
            stopMonitoring();
            startMonitoring();
        }
        System.out.println("Refresh interval set to " + seconds + " seconds");
    }
    
    /**
     * CLEAN ARCHITECTURE PRINCIPLE: Business Data Structure
     * This method provides business data in a standard format (Map),
     * not tied to any particular UI or database representation.
     */
    @Override
    public Map<String, Object> getStockQuote() {
        Map<String, Object> quotes = new HashMap<>();
        
        for (Map.Entry<String, Double> entry : watchlist.entrySet()) {
            Map<String, Object> quoteData = new HashMap<>();
            String symbol = entry.getKey();
            double price = entry.getValue();
            
            quoteData.put("price", price);
            quoteData.put("change", generateRandomChange());
            quoteData.put("volume", generateRandomVolume());
            quoteData.put("timestamp", new Date());
            
            quotes.put(symbol, quoteData);
        }
        
        return quotes;
    }
    
    @Override
    public double getCurrentPrice() {
        return currentPrice;
    }
    
    /**
     * CLEAN ARCHITECTURE PRINCIPLE: Business Logic Isolation
     * The business logic for calculating statistics is isolated here,
     * separated from UI or database concerns.
     */
    @Override
    public Map<String, Object> getPriceStatistics(String symbol) {
        Map<String, Object> statistics = new HashMap<>();
        
        if (watchlist.containsKey(symbol)) {
            // For a real implementation, we would need historical data
            // This is a simplified version that just returns some sample statistics
            double currentPrice = watchlist.get(symbol);
            
            // Add sample statistics
            statistics.put("open", currentPrice - generateRandomChange());
            statistics.put("high", currentPrice + 2.50);
            statistics.put("low", currentPrice - 1.75);
            statistics.put("close", currentPrice);
            statistics.put("volume", generateRandomVolume());
            statistics.put("52weekHigh", currentPrice + 15.0);
            statistics.put("52weekLow", currentPrice - 10.0);
            
            System.out.println("Generated statistics for " + symbol);
        } else {
            System.out.println("Symbol " + symbol + " not found in watchlist");
        }
        
        return statistics;
    }
    
    /**
     * Start the monitoring process to update stock prices at regular intervals
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: Framework Detail Isolation
     * The threading/scheduling mechanism (a framework detail) is isolated in this method,
     * not spread throughout the business logic.
     */
    public void startMonitoring() {
        if (isRunning) {
            System.out.println("Monitoring already started");
            return;
        }
        
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Refreshing stock prices...");
            for (String symbol : new ArrayList<>(watchlist.keySet())) {
                try {
                    // Simulate price updates - in a real implementation, this would connect to an API
                    double updatedPrice = watchlist.get(symbol) + generateRandomChange();
                    if (updatedPrice < 0) updatedPrice = 0.01; // Ensure price doesn't go negative
                    
                    watchlist.put(symbol, updatedPrice);
                    currentPrice = updatedPrice; // Update current price (this would be set to the currently selected stock in a real app)
                    System.out.println(symbol + ": " + updatedPrice);
                } catch (Exception e) {
                    System.err.println("Error updating " + symbol + ": " + e.getMessage());
                }
            }
        }, 0, refreshInterval, TimeUnit.SECONDS);
        
        isRunning = true;
        System.out.println("Stock monitoring started with interval of " + refreshInterval + " seconds");
    }
    
    /**
     * Stop the monitoring process
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: Resource Management
     * Properly cleaning up resources is part of good architecture design
     */
    public void stopMonitoring() {
        if (!isRunning) {
            System.out.println("Monitoring not started");
            return;
        }
        
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        isRunning = false;
        System.out.println("Stock monitoring stopped");
    }
    
    // CLEAN ARCHITECTURE PRINCIPLE: Helper Methods
    // Private helper methods keep the public interface clean
    // and encapsulate implementation details
    private double generateRandomPrice() {
        return 100.0 + Math.random() * 900.0; // Random price between 100 and 1000
    }
    
    private double generateRandomChange() {
        return (Math.random() - 0.5) * 5.0; // Random change between -2.5 and 2.5
    }
    
    private long generateRandomVolume() {
        return (long) (10000 + Math.random() * 990000); // Random volume between 10,000 and 1,000,000
    }
} 