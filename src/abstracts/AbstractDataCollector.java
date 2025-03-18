package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IDataCollector;

/**
 * Abstract implementation of the IDataCollector interface
 * Provides common functionality for data collectors
 */
public abstract class AbstractDataCollector implements IDataCollector {
    /**
     * The data source identifier
     */
    protected String source;
    
    /**
     * Constructor
     * @param source The data source identifier
     */
    public AbstractDataCollector(String source) {
        this.source = source;
    }
    
    /**
     * Gets the data source
     * @return The data source
     */
    public String getSource() {
        return source;
    }
    
    /**
     * Sets the data source
     * @param source The new data source
     */
    public void setSource(String source) {
        this.source = source;
    }
} 