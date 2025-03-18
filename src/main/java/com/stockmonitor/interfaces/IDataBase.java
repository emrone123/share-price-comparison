package com.stockmonitor.interfaces;

public interface IDataBase {
    void saveData(String symbol, double price);
    String fetchData(String symbol);
}
