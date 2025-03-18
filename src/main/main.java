package com.stockmonitor.app;

import com.stockmonitor.abstracts.*;
import com.stockmonitor.interfaces.*;

public class main {

    public static void main(String[] args) {
        // Initialize components
        IApplication app = new StockMonitorApplication();
        app.run();
    }
}

// Concrete implementation of IApplication
class StockMonitorApplication extends AbstractApplication {
    @Override
    public void run() {
        System.out.println("Stock Monitor Application is running...");

        // Initialize Data Collector
        IDataCollector dataCollector = new StockDataCollector("StockAPI");
        dataCollector.collectData();

        // Initialize Database
        IDataBase database = new StockDatabase();
        database.saveData("Sample Stock Data");

        // Initialize Stock Monitoring
        IStockMonitoring stockMonitoring = new StockPriceMonitor();
        stockMonitoring.monitorStockPrices();

        // Initialize Chart Display
        IChartDisplay chartDisplay = new StockChartDisplay("LineChart");
        chartDisplay.displayChart();

        // Initialize Logging
        ILoggingData logger = new StockLogger();
        logger.log("Application started successfully.");

        // Initialize Stock Prices
        IStockPrices stockPrices = new StockPriceTracker("AAPL");
        stockPrices.updateStockPrice(150.75);
        System.out.println("Current Stock Price: " + stockPrices.getCurrentPrice());
    }
}

// Concrete implementations of abstract classes
class StockDataCollector extends AbstractDataCollector {
    public StockDataCollector(String source) {
        super(source);
    }

    @Override
    public void collectData() {
        System.out.println("Collecting data from: " + source);
    }
}

class StockDatabase extends AbstractDatabase {
    @Override
    public void saveData(String data) {
        System.out.println("Saving data to database: " + data);
    }

    @Override
    public String fetchData() {
        return "Sample Stock Data from Database";
    }
}

class StockPriceMonitor extends AbstractStockMonitoring {
    @Override
    public void monitorStockPrices() {
        System.out.println("Monitoring stock prices...");
    }
}

class StockChartDisplay extends AbstractChartDisplay {
    public StockChartDisplay(String chartType) {
        super(chartType);
    }

    @Override
    public void displayChart() {
        System.out.println("Displaying " + chartType + " chart...");
    }
}

class StockLogger extends AbstractLogging {
    @Override
    public void log(String message) {
        System.out.println("Log: " + message);
    }
}

class StockPriceTracker extends AbstractStockPrices {
    public StockPriceTracker(String stockSymbol) {
        super(stockSymbol);
    }

    @Override
    public void updateStockPrice(double price) {
        System.out.println("Updating stock price for " + stockSymbol + " to: " + price);
    }

    @Override
    public double getCurrentPrice() {
        return 150.75; // Example price
    }
}