package com.stockmonitor.pricing;

public class StockPrices {
    private String symbol;
    private double price;

    public StockPrices(String symbol) {
        this.symbol = symbol;
        this.price = 100.0; // Default price
    }

    public double getCurrentPrice() {
        try {
            // Simulate API call to fetch real stock price
            // Replace with actual API call logic
            price += (Math.random() - 0.5) * 2;
            return price;
        } catch (Exception e) {
            System.err.println("Error fetching stock price: " + e.getMessage());
            return price; // Return last known price on error
        }
    }

    public String getSymbol() {
        return symbol;
    }
} 