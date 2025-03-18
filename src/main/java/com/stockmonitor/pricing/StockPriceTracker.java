package com.stockmonitor.pricing;

import com.stockmonitor.abstracts.AbstractStockPrices;

public class StockPriceTracker extends AbstractStockPrices {
    public StockPriceTracker(String stockSymbol) {
        super(stockSymbol);
    }

    @Override
    public void updateStockPrice(double price) {
        System.out.println("Updating stock price for " + stockSymbol + " to: " + price);
    }

    @Override
    public double getCurrentPrice() {
        return 150.75; // Example price
    }
} 