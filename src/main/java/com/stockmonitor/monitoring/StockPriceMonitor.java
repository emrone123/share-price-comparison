package com.stockmonitor.monitoring;

import com.stockmonitor.abstracts.AbstractStockMonitoring;

public class StockPriceMonitor extends AbstractStockMonitoring {
    @Override
    public void monitorStockPrices() {
        System.out.println("Monitoring stock prices...");
    }
} 