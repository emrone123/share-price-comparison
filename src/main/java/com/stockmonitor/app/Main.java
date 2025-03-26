package com.stockmonitor.app;

import com.stockmonitor.database.SQLiteDataStorage;
import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.IDataStorage;
import com.stockmonitor.interfaces.IStockMonitor;
import com.stockmonitor.monitor.BasicStockMonitor;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Main application class for the Stock Monitor application
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: UI Layer (Controllers)
 * This class represents the outermost layer in Clean Architecture, dealing with
 * UI concerns and controlling the flow of the application.
 * 
 * CLEAN ARCHITECTURE PRINCIPLE: Dependency Rule
 * The UI depends on inner layers (interfaces and domain) rather than concrete implementations,
 * thus following the dependency rule where dependencies point inward.
 */
public class Main extends Application {
    private static final String API_KEY = "655AOTU3KF8G1HEC";
    private LineChart<String, Number> lineChart;
    private TextArea logArea;
    
    // CLEAN ARCHITECTURE PRINCIPLE: Dependency Inversion
    // We depend on interfaces rather than concrete implementations
    private IStockMonitor stockMonitor;
    private IDataStorage dataStorage;

    @Override
    public void start(Stage primaryStage) {
        // CLEAN ARCHITECTURE PRINCIPLE: Dependency Injection
        // We inject concrete implementations of our interfaces
        // This allows for easy substitution of different implementations
        stockMonitor = new BasicStockMonitor();
        dataStorage = new SQLiteDataStorage();
        
        // UI setup code...
        // CLEAN ARCHITECTURE PRINCIPLE: Separation of Concerns
        // UI construction code is separated from business logic
        Label titleLabel = new Label("Stock Monitor Application");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        // Symbols input
        Label symbol1Label = new Label("Stock Symbol 1:");
        TextField symbol1Field = new TextField();
        symbol1Field.setPromptText("e.g., AAPL");
        
        Label symbol2Label = new Label("Stock Symbol 2 (for comparison):");
        TextField symbol2Field = new TextField();
        symbol2Field.setPromptText("e.g., MSFT");
        
        // Date range
        Label startDateLabel = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker(LocalDate.now().minusMonths(1));
        
        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker(LocalDate.now());
        
        // Buttons
        Button viewButton = new Button("View Stock");
        Button compareButton = new Button("Compare Stocks");
        Button addToWatchlistButton = new Button("Add to Watchlist");
        Button monitorButton = new Button("Start Monitoring");
        Button statsButton = new Button("View Statistics");
        
        // Chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Price");
        
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Stock Price Chart");
        
        // Log area
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(100);
        
        // CLEAN ARCHITECTURE PRINCIPLE: Use Case Boundary
        // UI event handlers call use cases through interfaces, maintaining clean boundaries
        viewButton.setOnAction(e -> {
            String symbol = symbol1Field.getText().trim().toUpperCase();
            if (symbol.isEmpty()) {
                showAlert("Please enter a stock symbol.");
                return;
            }
            
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            
            if (startDate == null || endDate == null) {
                showAlert("Please select start and end dates.");
                return;
            }
            
            if (startDate.isAfter(endDate)) {
                showAlert("Start date must be before end date.");
                return;
            }
            
            fetchAndDisplayStockData(symbol, startDate, endDate);
        });
        
        // More button handlers...
        compareButton.setOnAction(e -> {
            String symbol1 = symbol1Field.getText().trim().toUpperCase();
            String symbol2 = symbol2Field.getText().trim().toUpperCase();
            
            if (symbol1.isEmpty() || symbol2.isEmpty()) {
                showAlert("Please enter both stock symbols for comparison.");
                return;
            }
            
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            
            if (startDate == null || endDate == null) {
                showAlert("Please select start and end dates.");
                return;
            }
            
            if (startDate.isAfter(endDate)) {
                showAlert("Start date must be before end date.");
                return;
            }
            
            compareStocks(symbol1, symbol2, startDate, endDate);
        });
        
        // CLEAN ARCHITECTURE PRINCIPLE: Interface Boundary
        // UI interacts with business logic only through the interface
        addToWatchlistButton.setOnAction(e -> {
            String symbol = symbol1Field.getText().trim().toUpperCase();
            if (symbol.isEmpty()) {
                showAlert("Please enter a stock symbol to add to watchlist.");
                return;
            }
            
            stockMonitor.addStockToWatchlist(symbol);
            log("Added " + symbol + " to watchlist");
        });
        
        monitorButton.setOnAction(e -> {
            if (monitorButton.getText().equals("Start Monitoring")) {
                stockMonitor.setRefreshInterval(60); // 60 seconds
                stockMonitor.startMonitoring();
                monitorButton.setText("Stop Monitoring");
                log("Started monitoring stocks");
            } else {
                stockMonitor.stopMonitoring();
                monitorButton.setText("Start Monitoring");
                log("Stopped monitoring stocks");
            }
        });
        
        statsButton.setOnAction(e -> {
            String symbol = symbol1Field.getText().trim().toUpperCase();
            if (symbol.isEmpty()) {
                showAlert("Please enter a stock symbol to view statistics.");
                return;
            }
            
            Map<String, Object> stats = stockMonitor.getPriceStatistics(symbol);
            displayStatistics(symbol, stats);
        });
        
        // Layout setup...
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10));
        
        inputGrid.add(symbol1Label, 0, 0);
        inputGrid.add(symbol1Field, 1, 0);
        inputGrid.add(symbol2Label, 0, 1);
        inputGrid.add(symbol2Field, 1, 1);
        inputGrid.add(startDateLabel, 0, 2);
        inputGrid.add(startDatePicker, 1, 2);
        inputGrid.add(endDateLabel, 0, 3);
        inputGrid.add(endDatePicker, 1, 3);
        
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setPadding(new Insets(10));
        buttonGrid.add(viewButton, 0, 0);
        buttonGrid.add(compareButton, 1, 0);
        buttonGrid.add(addToWatchlistButton, 2, 0);
        buttonGrid.add(monitorButton, 3, 0);
        buttonGrid.add(statsButton, 4, 0);
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
            titleLabel,
            inputGrid,
            buttonGrid,
            lineChart,
            logArea
        );
        
        Scene scene = new Scene(layout, 800, 600);
        // Apply CSS styles
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setTitle("Stock Monitor Application");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        log("Stock Monitor Application started");
    }
    
    /**
     * Fetch and display stock data for a symbol
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: Use Case
     * This method represents a use case of fetching and displaying stock data.
     */
    private void fetchAndDisplayStockData(String symbol, LocalDate startDate, LocalDate endDate) {
        log("Fetching data for " + symbol + " from " + startDate + " to " + endDate);
        
        List<StockData> stockDataList = fetchStockData(symbol, startDate, endDate);
        displayStockData(stockDataList);
    }
    
    /**
     * Fetch stock data from API or storage
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: Boundary Crossing
     * This method interacts with external systems (API) and inner layers (storage),
     * managing the boundary between them.
     */
    private List<StockData> fetchStockData(String symbol, LocalDate startDate, LocalDate endDate) {
        List<StockData> stockDataList = new ArrayList<>();
        
        try {
            // CLEAN ARCHITECTURE PRINCIPLE: External Interface Adapter
            // The Alpha Vantage API interaction is isolated here, not mixed with business logic
            String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY" +
                            "&symbol=" + symbol +
                            "&outputsize=full" +
                            "&datatype=csv" +
                            "&apikey=" + API_KEY;
            
            log("Connecting to Alpha Vantage API...");
            
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
                            StockData stockData = new StockData(symbol, price, date);
                            stockDataList.add(stockData);
                            
                            // CLEAN ARCHITECTURE PRINCIPLE: Interface Boundary
                            // Interact with storage through its interface
                            StockPrice stockPrice = new StockPrice(symbol, price, date);
                            dataStorage.saveStockData(stockPrice);
                        }
                    }
                }
            }
            
            log("Successfully fetched data for " + symbol + ", got " + stockDataList.size() + " data points");
        } catch (Exception e) {
            log("Error fetching stock data: " + e.getMessage());
            
            // CLEAN ARCHITECTURE PRINCIPLE: Fallback Strategy
            // Attempt to retrieve data from storage if API fails
            log("Attempting to retrieve data from storage...");
            List<StockPrice> storedData = dataStorage.retrieveStockData(symbol, startDate);
            
            for (StockPrice price : storedData) {
                if (!price.getDate().isAfter(endDate)) {
                    stockDataList.add(new StockData(price.getSymbol(), price.getPrice(), price.getDate()));
                }
            }
            
            if (stockDataList.isEmpty()) {
                // If still empty, provide sample data for demonstration
                log("No data found in storage. Using sample data for " + symbol);
                for (int i = 0; i < 30; i++) {
                    LocalDate date = startDate.plusDays(i);
                    if (date.isAfter(endDate)) break;
                    
                    double price = 100 + Math.random() * 20; // Random price between 100-120
                    stockDataList.add(new StockData(symbol, price, date));
                }
            } else {
                log("Retrieved " + stockDataList.size() + " data points from storage");
            }
        }
        
        return stockDataList;
    }
    
    /**
     * Display stock data in the chart
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: Presentation Logic
     * UI-specific presentation logic is isolated in this method
     */
    private void displayStockData(List<StockData> stockDataList) {
        lineChart.getData().clear();
        
        if (stockDataList.isEmpty()) {
            log("No data to display");
            return;
        }
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(stockDataList.get(0).getSymbol());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (StockData data : stockDataList) {
            series.getData().add(
                new XYChart.Data<>(data.getDate().format(formatter), data.getPrice())
            );
        }
        
        lineChart.getData().add(series);
        log("Chart updated with data for " + stockDataList.get(0).getSymbol());
    }
    
    /**
     * Compare two stocks by fetching and displaying their data
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: Composite Use Case
     * This method combines multiple simpler use cases to create a more complex one
     */
    private void compareStocks(String symbol1, String symbol2, LocalDate startDate, LocalDate endDate) {
        log("Comparing stocks: " + symbol1 + " vs " + symbol2);
        
        List<StockData> stockData1 = fetchStockData(symbol1, startDate, endDate);
        List<StockData> stockData2 = fetchStockData(symbol2, startDate, endDate);
        
        lineChart.getData().clear();
        
        if (stockData1.isEmpty() || stockData2.isEmpty()) {
            log("Insufficient data for comparison");
            return;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(symbol1);
        
        for (StockData data : stockData1) {
            series1.getData().add(
                new XYChart.Data<>(data.getDate().format(formatter), data.getPrice())
            );
        }
        
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName(symbol2);
        
        for (StockData data : stockData2) {
            series2.getData().add(
                new XYChart.Data<>(data.getDate().format(formatter), data.getPrice())
            );
        }
        
        lineChart.getData().addAll(series1, series2);
        log("Chart updated with comparison data");
    }
    
    /**
     * Display statistics for a stock
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: Presentation Adapter
     * This method formats domain data for UI presentation
     */
    private void displayStatistics(String symbol, Map<String, Object> stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("Statistics for ").append(symbol).append(":\n");
        
        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        log(sb.toString());
    }
    
    /**
     * Log a message to the UI log area
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: UI-Specific Helper
     * This helper method isolates UI-specific logging concerns
     */
    private void log(String message) {
        logArea.appendText(message + "\n");
    }
    
    /**
     * Show an alert dialog
     * 
     * CLEAN ARCHITECTURE PRINCIPLE: UI-Specific Helper
     * This helper method isolates UI-specific alert display concerns
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * CLEAN ARCHITECTURE PRINCIPLE: Inner Layer Entity
     * This inner class represents a data structure used within the UI layer
     * but is based on the domain entity (StockPrice)
     */
    private static class StockData {
        private final String symbol;
        private final double price;
        private final LocalDate date;
        
        public StockData(String symbol, double price, LocalDate date) {
            this.symbol = symbol;
            this.price = price;
            this.date = date;
        }
        
        public String getSymbol() { return symbol; }
        public double getPrice() { return price; }
        public LocalDate getDate() { return date; }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}