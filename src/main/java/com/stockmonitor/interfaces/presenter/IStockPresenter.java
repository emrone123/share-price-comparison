package com.stockmonitor.interfaces.presenter;

import com.stockmonitor.domain.StockPrice;
import java.util.List;

// ISP: Interface specific to presentation operations
public interface IStockPresenter {
    void displayStockData(List<StockPrice> stockPrices);
    void displayComparisonData(List<List<StockPrice>> comparisonData);
    void showError(String message);
} 