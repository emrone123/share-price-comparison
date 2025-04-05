package com.stockmonitor.display;

import com.stockmonitor.abstracts.AbstractChartDisplay;
import com.stockmonitor.domain.StockPrice;
import java.util.List;

/**
 * Stock chart display implementation for web application
 * 
 * This displays stock charts without JavaFX dependencies
 */
public class StockChartDisplay extends AbstractChartDisplay {
    
    public StockChartDisplay(String chartType) {
        super(chartType);
    }

    @Override
    public void displayChart() {
        System.out.println("Displaying " + chartType + " chart...");
    }
    
    @Override
    public void displayStockData(List<StockPrice> stockPrices) {
        if (stockPrices == null || stockPrices.isEmpty()) {
            System.out.println("No stock data to display");
            return;
        }
        
        String symbol = stockPrices.get(0).getSymbol();
        System.out.println("Displaying " + chartType + " chart for " + symbol + 
                " with " + stockPrices.size() + " data points");
        
        // Print summary
        double latest = stockPrices.get(stockPrices.size() - 1).getPrice();
        System.out.println("Latest price: $" + String.format("%.2f", latest));
    }
    
    @Override
    public void compareStocks(List<StockPrice> stockPrices1, List<StockPrice> stockPrices2) {
        if (stockPrices1 == null || stockPrices1.isEmpty() || 
            stockPrices2 == null || stockPrices2.isEmpty()) {
            System.out.println("Insufficient stock data for comparison");
            return;
        }
        
        String symbol1 = stockPrices1.get(0).getSymbol();
        String symbol2 = stockPrices2.get(0).getSymbol();
        
        System.out.println("Comparing " + symbol1 + " vs " + symbol2 + 
                " using " + chartType + " chart");
        
        // Calculate simple performance metrics
        double firstPrice1 = stockPrices1.get(0).getPrice();
        double lastPrice1 = stockPrices1.get(stockPrices1.size() - 1).getPrice();
        double change1 = ((lastPrice1 - firstPrice1) / firstPrice1) * 100;
        
        double firstPrice2 = stockPrices2.get(0).getPrice();
        double lastPrice2 = stockPrices2.get(stockPrices2.size() - 1).getPrice();
        double change2 = ((lastPrice2 - firstPrice2) / firstPrice2) * 100;
        
        System.out.println(symbol1 + " change: " + String.format("%.2f", change1) + "%");
        System.out.println(symbol2 + " change: " + String.format("%.2f", change2) + "%");
    }
} 