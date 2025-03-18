package com.stockmonitor.interfaces;

public interface IStockPrices {
    void updateStockPrice(double price);
    double getCurrentPrice();
}