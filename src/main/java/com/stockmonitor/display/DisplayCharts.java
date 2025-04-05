package com.stockmonitor.display;

import com.stockmonitor.interfaces.IChartDisplay;
import com.stockmonitor.domain.StockPrice;
import java.util.List;

/**
 * Alternative chart display implementation
 * 
 * Implements the display interface for web application use
 */
public class DisplayCharts implements IChartDisplay {
    @Override
    public void displayChart() {
        System.out.println("Displaying chart...");
    }
    
    @Override
    public void displayStockData(List<StockPrice> stockPrices) {
        System.out.println("Displaying stock data for " + 
                (stockPrices.isEmpty() ? "empty data" : stockPrices.get(0).getSymbol()) + 
                " with " + stockPrices.size() + " data points");
    }
    
    @Override
    public void compareStocks(List<StockPrice> stockPrices1, List<StockPrice> stockPrices2) {
        String symbol1 = stockPrices1.isEmpty() ? "unknown" : stockPrices1.get(0).getSymbol();
        String symbol2 = stockPrices2.isEmpty() ? "unknown" : stockPrices2.get(0).getSymbol();
        
        System.out.println("Comparing stocks: " + symbol1 + " vs " + symbol2);
    }
} 