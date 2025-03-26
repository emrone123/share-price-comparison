package com.stockmonitor.filters;

public class DecryptionFilter implements IFilter {
    @Override
    public Object process(Object input) {
        return decrypt(input);
    }

    private Object decrypt(Object input) {
        // Simple processing logic
        return input;
    }
} 