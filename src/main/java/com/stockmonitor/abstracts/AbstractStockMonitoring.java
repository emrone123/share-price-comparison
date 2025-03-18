package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IStockMonitoring;

public abstract class AbstractStockMonitoring implements IStockMonitoring {
    public abstract void monitorStockPrices();

    // Common monitoring functionality can be added here
}
