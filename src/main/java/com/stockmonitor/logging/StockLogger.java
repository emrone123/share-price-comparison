package com.stockmonitor.logging;

import com.stockmonitor.abstracts.AbstractLogging;

public class StockLogger extends AbstractLogging {
    @Override
    public void log(String message) {
        System.out.println("Log: " + message);
    }
} 