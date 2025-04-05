package com.stockmonitor.domain;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Domain class for storing stock price information
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Entity
 * This class represents a core business entity (StockPrice) that encapsulates
 * essential business rules and data structures.
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Single Responsibility
 * This class has only one reason to change: if the definition of what constitutes
 * a stock price changes in the business domain.
 */
@Entity
@Table(name = "stock_prices")
public class StockPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String symbol;
    private double price;
    private LocalDate date;
    private double change;
    private double percentChange;
    private long volume;
    
    /**
     * Default constructor required by JPA
     */
    public StockPrice() {
    }

    /**
     * Create a new StockPrice with symbol, price, and date
     * 
     * @param symbol The stock symbol
     * @param price The current price
     * @param date The date of the price
     */
    public StockPrice(String symbol, double price, LocalDate date) {
        this.symbol = symbol;
        this.price = price;
        this.date = date;
        this.change = 0.0;
        this.percentChange = 0.0;
        this.volume = 0;
    }

    /**
     * Create a new StockPrice with symbol, price, date, change, and volume
     * 
     * @param symbol The stock symbol
     * @param price The current price
     * @param date The date of the price
     * @param change The price change from previous close
     * @param percentChange The percentage price change
     * @param volume The trading volume
     */
    public StockPrice(String symbol, double price, LocalDate date, double change, double percentChange, long volume) {
        this.symbol = symbol;
        this.price = price;
        this.date = date;
        this.change = change;
        this.percentChange = percentChange;
        this.volume = volume;
    }
    
    /**
     * Get the ID
     * 
     * @return The ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the ID
     * 
     * @param id The ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Set the symbol
     * 
     * @param symbol The stock symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    /**
     * Set the price
     * 
     * @param price The price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Set the date
     * 
     * @param date The date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get the stock symbol
     * 
     * @return The stock symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Get the stock price
     * 
     * @return The stock price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get the date of the price
     * 
     * @return The date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the price change from previous close
     * 
     * @return The price change
     */
    public double getChange() {
        return change;
    }

    /**
     * Get the percentage price change
     * 
     * @return The percentage change
     */
    public double getPercentChange() {
        return percentChange;
    }

    /**
     * Get the trading volume
     * 
     * @return The volume
     */
    public long getVolume() {
        return volume;
    }

    /**
     * Set the price change
     * 
     * @param change The price change
     */
    public void setChange(double change) {
        this.change = change;
    }

    /**
     * Set the percentage price change
     * 
     * @param percentChange The percentage change
     */
    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }

    /**
     * Set the trading volume
     * 
     * @param volume The volume
     */
    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockPrice that = (StockPrice) o;
        return Double.compare(that.price, price) == 0 &&
               Objects.equals(symbol, that.symbol) &&
               Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, price, date);
    }

    @Override
    public String toString() {
        return "StockPrice{" +
               "symbol='" + symbol + '\'' +
               ", price=" + price +
               ", date=" + date +
               ", change=" + change +
               ", percentChange=" + percentChange +
               ", volume=" + volume +
               '}';
    }
} 