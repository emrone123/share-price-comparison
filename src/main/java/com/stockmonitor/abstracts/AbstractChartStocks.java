package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IChartStocks;

public abstract class AbstractChartStocks implements IChartStocks {
    @Override
    public abstract void updateStockChart();
}