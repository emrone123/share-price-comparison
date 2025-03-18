package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IChartDisplay;

public abstract class AbstractChartDisplay implements IChartDisplay {
    protected String chartType;

    public AbstractChartDisplay(String chartType) {
        this.chartType = chartType;
    }

    public abstract void displayChart();
}
