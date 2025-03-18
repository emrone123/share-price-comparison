package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.ILoggingRing;

public abstract class AbstractLoggingRing implements ILoggingRing {
    @Override
    public abstract void logRing(String message);
}