package com.stockmonitor.logging;

import com.stockmonitor.interfaces.ILoggingData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger implements ILoggingData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] " + message);
    }
} 