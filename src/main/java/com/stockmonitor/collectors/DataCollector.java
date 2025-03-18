package com.stockmonitor.collectors;

import com.stockmonitor.interfaces.IDataCollector;

public class DataCollector implements IDataCollector {
    @Override
    public void collectData() {
        System.out.println("Collecting data...");
    }
} 