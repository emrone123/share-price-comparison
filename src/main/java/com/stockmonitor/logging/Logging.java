package com.stockmonitor.logging;

import com.stockmonitor.interfaces.ILoggingData;

public class Logging implements ILoggingData {
    @Override
    public void log(String message) {
        System.out.println("Log: " + message);
    }
} 