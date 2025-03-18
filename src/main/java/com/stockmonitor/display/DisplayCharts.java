package com.stockmonitor.display;

import com.stockmonitor.interfaces.IChartDisplay;

public class DisplayCharts implements IChartDisplay {
    @Override
    public void displayChart() {
        System.out.println("Displaying chart...");
    }
} 