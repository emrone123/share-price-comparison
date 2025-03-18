package com.stockmonitor.monitoring;

import com.stockmonitor.interfaces.IStockMonitoring;

public class MonitoringRing implements IStockMonitoring {
    @Override
    public void monitorStockPrices() {
        System.out.println("Monitoring stock prices...");
    }
} 