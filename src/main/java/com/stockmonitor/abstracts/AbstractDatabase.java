package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IDataBase;

public abstract class AbstractDatabase implements IDataBase {
    public abstract void saveData(String data);
    public abstract String fetchData();

    // Common database functionality can be added here
}
