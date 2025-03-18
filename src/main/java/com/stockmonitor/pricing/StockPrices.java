package com.stockmonitor.pricing;

public class StockPrices {
    private String symbol;
    private double price;

    public StockPrices(String symbol) {
        this.symbol = symbol;
        this.price = 100.0; // Default price
    }

    public double getCurrentPrice() {
        // Simulate price update
        price += (Math.random() - 0.5) * 2;
        return price;
    }

    public String getSymbol() {
        return symbol;
    }
} 