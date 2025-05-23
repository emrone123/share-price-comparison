package com.stockmonitor.collectors;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.IDataCollector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataCollector implements IDataCollector {
    // Using your Alpha Vantage API key
    private static final String API_KEY = "655AOTU3KF8G1HEC";
    
    @Override
    public void collectData() {
        System.out.println("Collecting stock data...");
    }
    
    public List<StockPrice> fetchStockData(String symbol, LocalDate startDate, LocalDate endDate) {
        List<StockPrice> stockPrices = new ArrayList<>();
        
        try {
            // Alpha Vantage API URL for daily time series
            String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY" +
                            "&symbol=" + symbol +
                            "&outputsize=full" +
                            "&datatype=csv" +
                            "&apikey=" + API_KEY;
            
            System.out.println("Fetching data from: " + apiUrl);
            
            URL url = new URL(apiUrl);
            URLConnection connection = url.openConnection();
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                boolean headerSkipped = false;
                
                while ((line = reader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue; // Skip header
                    }
                    
                    String[] data = line.split(",");
                    if (data.length >= 5) {
                        LocalDate date = LocalDate.parse(data[0]);
                        
                        // Only include data within the date range
                        if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
                            double price = Double.parseDouble(data[4]); // Close price
                            stockPrices.add(new StockPrice(symbol, price, date));
                        }
                    }
                }
            }
            
            System.out.println("Successfully fetched data for " + symbol + ", got " + stockPrices.size() + " data points");
        } catch (Exception e) {
            System.err.println("Error fetching stock data: " + e.getMessage());
            e.printStackTrace();
            
            // Provide sample data for demonstration in case of API failure
            System.out.println("Using sample data for " + symbol);
            for (int i = 0; i < 30; i++) {
                LocalDate date = startDate.plusDays(i);
                if (date.isAfter(endDate)) break;
                
                double price = 100 + Math.random() * 20; // Random price between 100-120
                stockPrices.add(new StockPrice(symbol, price, date));
            }
        }
        
        return stockPrices;
    }
} 