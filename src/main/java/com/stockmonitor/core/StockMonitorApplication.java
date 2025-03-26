package com.stockmonitor.core;

import com.stockmonitor.filters.IFilter;
import com.stockmonitor.filters.DecryptionFilter;
import java.util.ArrayList;
import java.util.List;

public class StockMonitorApplication {
    private final List<IFilter> filters;

    public StockMonitorApplication() {
        this.filters = new ArrayList<>();
        // Initialize only the DecryptionFilter
        filters.add(new DecryptionFilter());
    }

    public Object processData(Object input) {
        Object result = input;
        for (IFilter filter : filters) {
            result = filter.process(result);
        }
        return result;
    }

    public void run() {
        System.out.println("Stock Monitor Application is running...");
    }
} 