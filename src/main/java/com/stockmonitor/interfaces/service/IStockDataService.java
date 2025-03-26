package com.stockmonitor.interfaces.service;

import com.stockmonitor.domain.StockPrice;
import java.time.LocalDate;
import java.util.List;

// ISP: Interface specific to external data service operations
public interface IStockDataService {
    List<StockPrice> fetchStockData(String symbol, LocalDate startDate, LocalDate endDate);
} 