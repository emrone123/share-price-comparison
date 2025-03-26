package com.stockmonitor.interfaces.repository;

import com.stockmonitor.domain.StockPrice;
import java.time.LocalDate;
import java.util.List;

// ISP: Interface specific to repository operations
public interface IStockRepository {
    void saveStockPrice(StockPrice stockPrice);
    List<StockPrice> getStockPrices(String symbol, LocalDate startDate, LocalDate endDate);
    boolean hasStockData(String symbol, LocalDate startDate, LocalDate endDate);
} 