package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IDataBase;

public abstract class AbstractDatabase implements IDataBase {
    public abstract void saveData(String symbol, double price);
    public abstract String fetchData(String symbol);

    // Common database functionality can be added here
}
