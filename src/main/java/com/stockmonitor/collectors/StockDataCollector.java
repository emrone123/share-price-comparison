package com.stockmonitor.collectors;

import com.stockmonitor.abstracts.AbstractDataCollector;

public class StockDataCollector extends AbstractDataCollector {
    public StockDataCollector(String source) {
        super(source);
    }

    @Override
    public void collectData() {
        System.out.println("Collecting data from: " + source);
    }
} 