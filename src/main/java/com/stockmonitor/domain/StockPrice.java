package com.stockmonitor.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Domain class for storing stock price information
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Entity
 * This class represents a core business entity (StockPrice) that encapsulates
 * essential business rules and data structures. It doesn't depend on any
 * framework or external library, making it completely independent of UI, database,
 * or external services.
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Single Responsibility
 * This class has only one reason to change: if the definition of what constitutes
 * a stock price changes in the business domain.
 */
public class StockPrice {
    // CLEAN ARCHITECTURE PRINCIPLE: Encapsulation
    // Data fields are private with immutable core properties
    private final String symbol;
    private final double price;
    private final LocalDate date;
    private double change;
    private double percentChange;
    private long volume;

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