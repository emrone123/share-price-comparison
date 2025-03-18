package com.stockmonitor.database;

import com.stockmonitor.abstracts.AbstractDatabase;

public class StockDatabase extends AbstractDatabase {
    @Override
    public void saveData(String data) {
        System.out.println("Saving data to database: " + data);
    }

    @Override
    public String fetchData() {
        return "Sample Stock Data from Database";
    }
} 