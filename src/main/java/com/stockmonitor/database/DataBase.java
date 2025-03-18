package com.stockmonitor.database;

import com.stockmonitor.interfaces.IDataBase;

public class DataBase implements IDataBase {
    @Override
    public void saveData(String symbol, double price) {
        System.out.println("Saving data: " + symbol + ", " + price);
    }

    @Override
    public String fetchData(String symbol) {
        return "Sample Data for " + symbol;
    }
} 