package com.stockmonitor.interfaces;

// ISP: This interface is focused on database operations only.
public interface IDataBase {
    void saveData(String symbol, double price);
    String fetchData(String symbol);
}

// Interface Segregation Principle (ISP)
// Effectiveness: Reduces unnecessary dependencies and increases modularity.
// Reasoning: By creating specific interfaces, classes only depend on the methods they actually use, leading to a more flexible and adaptable system
// Location: Specific interfaces like IChartDisplay, IStockMonitoring