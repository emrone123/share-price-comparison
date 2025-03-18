package com.stockmonitor.database;

import com.stockmonitor.interfaces.IDataBase;

public class DataBase implements IDataBase {
    @Override
    public void saveData(String data) {
        System.out.println("Saving data: " + data);
    }

    @Override
    public String fetchData() {
        return "Sample Data";
    }
} 