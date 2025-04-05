package com.stockmonitor.service;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.repository.StockPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of StockDataService for Yahoo Finance
 * 
 * This follows the:
 * - Adapter Pattern: Adapts Yahoo Finance API to our domain model
 * - Service Layer Pattern: Part of the service layer in N-tier architecture
 * - SOA: Implements a clearly defined service
 */
@Service
public class YahooFinanceStockDataService implements StockDataService {
    private static final Logger logger = LoggerFactory.getLogger(YahooFinanceStockDataService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Random random = new Random(); // For demo purposes
    
    private final StockPriceRepository stockPriceRepository;
    
    @Autowired
    public YahooFinanceStockDataService(StockPriceRepository stockPriceRepository) {
        this.stockPriceRepository = stockPriceRepository;
    }

    @Override
    public List<StockPrice> fetchStockData(String symbol, LocalDate startDate, LocalDate endDate) {
        logger.info("Fetching stock data for {} from {} to {}", symbol, startDate, endDate);
        
        // First check if we already have this data in our repository
        List<StockPrice> existingData = stockPriceRepository.findBySymbolAndDateBetweenOrderByDateAsc(
                symbol, startDate, endDate);
        
        if (!existingData.isEmpty()) {
            logger.info("Found {} existing records for {}", existingData.size(), symbol);
            return existingData;
        }
        
        // If not, fetch from external service (Yahoo Finance)
        // For demo purposes, we'll generate random data
        List<StockPrice> generatedData = generateSampleData(symbol, startDate, endDate);
        
        // Save to database for future queries
        stockPriceRepository.saveAll(generatedData);
        
        return generatedData;
    }
    
    @Override
    public StockPrice saveStockPrice(StockPrice stockPrice) {
        return stockPriceRepository.save(stockPrice);
    }
    
    @Override
    public List<StockPrice> saveAllStockPrices(List<StockPrice> stockPrices) {
        return stockPriceRepository.saveAll(stockPrices);
    }
    
    @Override
    public StockPrice getLatestStockPrice(String symbol) {
        return stockPriceRepository.findTopBySymbolOrderByDateDesc(symbol);
    }
    
    /**
     * Scheduled job to update stock data for commonly used symbols
     * Demonstrates the use of the Observer pattern through Spring's scheduling
     */
    @Scheduled(fixedRate = 60000)
    public void scheduledStockUpdate() {
        logger.info("Running scheduled stock update");
        List<String> commonStocks = List.of("AAPL", "MSFT", "GOOGL", "AMZN");
        
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(5);
        
        for (String symbol : commonStocks) {
            try {
                List<StockPrice> prices = this.fetchStockData(symbol, startDate, endDate);
                logger.info("Updated {} prices for {}", prices.size(), symbol);
            } catch (Exception e) {
                logger.error("Error updating prices for {}: {}", symbol, e.getMessage());
            }
        }
    }
    
    // Helper method to generate sample data for demonstration
    private List<StockPrice> generateSampleData(String symbol, LocalDate startDate, LocalDate endDate) {
        List<StockPrice> results = new ArrayList<>();
        LocalDate currentDate = startDate;
        
        double basePrice = switch (symbol) {
            case "AAPL" -> 180.0;
            case "MSFT" -> 340.0;
            case "GOOGL" -> 140.0;
            case "AMZN" -> 178.0;
            default -> 100.0;
        };
        
        while (!currentDate.isAfter(endDate)) {
            // Skip weekends (simplified)
            if (currentDate.getDayOfWeek().getValue() <= 5) {
                // Generate price with some random variation
                double dailyChange = (random.nextDouble() - 0.45) * 5; // -2.25% to +2.75%
                double price = basePrice * (1 + dailyChange / 100);
                basePrice = price; // For next iteration
                
                long volume = 1000000 + random.nextInt(9000000);
                
                StockPrice stockPrice = new StockPrice(
                    symbol,
                    Math.round(price * 100) / 100.0, // Round to 2 decimals
                    currentDate,
                    dailyChange > 0 ? Math.round(dailyChange * 100) / 100.0 : Math.round(dailyChange * 100) / 100.0,
                    dailyChange,
                    volume
                );
                
                results.add(stockPrice);
            }
            
            currentDate = currentDate.plusDays(1);
        }
        
        return results;
    }
} 