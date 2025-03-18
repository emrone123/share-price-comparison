package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.ILoggingData;

public abstract class AbstractLogging implements ILoggingData {
    public abstract void log(String message);

    // Common logging functionality can be added here
}
