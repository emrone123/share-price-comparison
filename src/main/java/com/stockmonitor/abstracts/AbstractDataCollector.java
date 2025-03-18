package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IDataCollector;

public abstract class AbstractDataCollector implements IDataCollector {
    protected String source;

    public AbstractDataCollector(String source) {
        this.source = source;
    }

    public abstract void collectData();
}
