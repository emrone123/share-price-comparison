package com.stockmonitor;

import com.stockmonitor.abstracts.AbstractDataCollector;

public class DataCollector extends AbstractDataCollector {

    public DataCollector(String source) {
        super(source);
    }

    @Override
    public void collectData() {
        System.out.println("Collecting stock data from " + source);
    }
}
