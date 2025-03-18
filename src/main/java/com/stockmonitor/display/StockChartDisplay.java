package com.stockmonitor.display;

import com.stockmonitor.abstracts.AbstractChartDisplay;

public class StockChartDisplay extends AbstractChartDisplay {
    public StockChartDisplay(String chartType) {
        super(chartType);
    }

    @Override
    public void displayChart() {
        System.out.println("Displaying " + chartType + " chart...");
    }
} 