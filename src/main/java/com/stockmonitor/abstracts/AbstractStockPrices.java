package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IStockPrices;

public abstract class AbstractStockPrices implements IStockPrices {
    protected String stockSymbol;

    public AbstractStockPrices(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public abstract void updateStockPrice(double price);
    public abstract double getCurrentPrice();
}