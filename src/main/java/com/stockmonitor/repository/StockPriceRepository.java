package com.stockmonitor.repository;

import com.stockmonitor.domain.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for StockPrice entities
 * 
 * This follows the Repository pattern from Domain-Driven Design,
 * and is part of the data access layer in the N-tier architecture.
 */
@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    
    /**
     * Find stock prices by symbol
     * @param symbol Stock symbol
     * @return List of stock prices
     */
    List<StockPrice> findBySymbol(String symbol);
    
    /**
     * Find stock prices by symbol and date range
     * @param symbol Stock symbol
     * @param startDate Start date
     * @param endDate End date
     * @return List of stock prices
     */
    List<StockPrice> findBySymbolAndDateBetweenOrderByDateAsc(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * Find the most recent stock price for a given symbol
     * @param symbol Stock symbol
     * @return Most recent stock price
     */
    StockPrice findTopBySymbolOrderByDateDesc(String symbol);
} 