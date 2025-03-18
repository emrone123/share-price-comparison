package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IChartMonitor;

public abstract class AbstractChartMonitor implements IChartMonitor {
    @Override
    public abstract void monitorCharts();
}