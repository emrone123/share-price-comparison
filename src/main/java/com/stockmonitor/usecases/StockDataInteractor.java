package com.stockmonitor.usecases;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.repository.IStockRepository;
import com.stockmonitor.interfaces.service.IStockDataService;
import java.time.LocalDate;
import java.util.List;

// Implementation of use case interactors following Clean Architecture
public class StockDataInteractor {
    private final IStockRepository repository;
    private final IStockDataService dataService;
    
    // DIP: Dependencies injected through constructor
    public StockDataInteractor(IStockRepository repository, IStockDataService dataService) {
        this.repository = repository;
        this.dataService = dataService;
    }
    
    // Get stock data, first trying repository, then external service
    public List<StockPrice> getStockData(String symbol, LocalDate startDate, LocalDate endDate) {
        // Try to get from repository first
        List<StockPrice> localData = repository.getStockPrices(symbol, startDate, endDate);
        
        // If data is not available locally, fetch from external service
        if (localData.isEmpty()) {
            List<StockPrice> remoteData = dataService.fetchStockData(symbol, startDate, endDate);
            // Save to repository for future use
            for (StockPrice stockPrice : remoteData) {
                repository.saveStockPrice(stockPrice);
            }
            return remoteData;
        }
        
        return localData;
    }
    
    // Compare stock data for two companies
    public List<List<StockPrice>> compareStocks(String symbol1, String symbol2, 
                                              LocalDate startDate, LocalDate endDate) {
        List<StockPrice> stockData1 = getStockData(symbol1, startDate, endDate);
        List<StockPrice> stockData2 = getStockData(symbol2, startDate, endDate);
        
        return List.of(stockData1, stockData2);
    }
} 