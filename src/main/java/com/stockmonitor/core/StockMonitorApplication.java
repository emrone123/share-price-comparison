package com.stockmonitor.core;

import com.stockmonitor.abstracts.AbstractApplication;
import com.stockmonitor.interfaces.*;
import com.stockmonitor.pricing.StockPrices;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StockMonitorApplication extends AbstractApplication {
    private final IDataCollector dataCollector;
    private final IDataBase database;
    private final ILoggingData logger;
    private final IChartDisplay chartDisplay;
    private final IStockMonitoring stockMonitoring;

    private TextArea logArea;
    private VBox root;
    private TableView<StockData> stockTable;
    private LineChart<Number, Number> priceChart;
    private Map<String, XYChart.Series<Number, Number>> stockSeries;
    private Map<String, StockPrices> stockTrackers;
    private ScheduledExecutorService scheduler;
    private int xSeriesData = 0;

    public StockMonitorApplication(IDataCollector dataCollector, IDataBase database, ILoggingData logger,
                                   IChartDisplay chartDisplay, IStockMonitoring stockMonitoring) {
        this.dataCollector = dataCollector;
        this.database = database;
        this.logger = logger;
        this.chartDisplay = chartDisplay;
        this.stockMonitoring = stockMonitoring;

        // Initialize collections
        stockSeries = new HashMap<>();
        stockTrackers = new HashMap<>();
        
        // Create UI components
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefRowCount(6);
        logArea.setStyle("-fx-font-family: 'monospace';");

        // Initialize stock table
        createStockTable();

        // Initialize price chart
        createPriceChart();

        // Start the scheduler for real-time updates
        scheduler = Executors.newScheduledThreadPool(1);
    }

    private void createStockTable() {
        stockTable = new TableView<>();
        
        TableColumn<StockData, String> symbolCol = new TableColumn<>("Symbol");
        symbolCol.setCellValueFactory(data -> data.getValue().symbolProperty());
        
        TableColumn<StockData, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        
        TableColumn<StockData, Double> changeCol = new TableColumn<>("Change");
        changeCol.setCellValueFactory(data -> data.getValue().changeProperty().asObject());
        
        stockTable.getColumns().addAll(symbolCol, priceCol, changeCol);
        stockTable.setItems(FXCollections.observableArrayList());
    }

    private void createPriceChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Price");
        
        priceChart = new LineChart<>(xAxis, yAxis);
        priceChart.setTitle("Stock Prices");
        priceChart.setAnimated(false);
    }

    private void log(String message) {
        Platform.runLater(() -> {
            logArea.appendText(message + "\n");
        });
    }

    public void setRoot(VBox root) {
        this.root = root;

        // Create control buttons
        Button addStockBtn = new Button("Add Stock");
        Button refreshBtn = new Button("Refresh");
        TextField symbolInput = new TextField();
        symbolInput.setPromptText("Enter stock symbol");
        
        HBox controls = new HBox(10);
        controls.getChildren().addAll(symbolInput, addStockBtn, refreshBtn);
        controls.setPadding(new Insets(10));

        // Add components to root
        root.getChildren().addAll(
            controls,
            priceChart,
            stockTable,
            new Label("Log:"),
            logArea
        );

        // Set growth properties
        VBox.setVgrow(priceChart, Priority.ALWAYS);
        VBox.setVgrow(stockTable, Priority.ALWAYS);
        VBox.setVgrow(logArea, Priority.ALWAYS);

        // Add event handlers
        addStockBtn.setOnAction(e -> {
            String symbol = symbolInput.getText().toUpperCase();
            if (!symbol.isEmpty()) {
                addStock(symbol);
                symbolInput.clear();
            }
        });

        refreshBtn.setOnAction(e -> refreshAllStocks());
    }

    private void addStock(String symbol) {
        if (!stockTrackers.containsKey(symbol)) {
            StockPrices tracker = new StockPrices(symbol);
            stockTrackers.put(symbol, tracker);
            
            // Create series for the chart
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(symbol);
            stockSeries.put(symbol, series);
            priceChart.getData().add(series);
            
            // Add to table
            StockData stockData = new StockData(symbol, 0.0, 0.0);
            stockTable.getItems().add(stockData);
            
            log("Added stock: " + symbol);
            updateStock(symbol);
        }
    }

    private void updateStock(String symbol) {
        StockPrices tracker = stockTrackers.get(symbol);
        if (tracker != null) {
            double price = tracker.getCurrentPrice();
            XYChart.Series<Number, Number> series = stockSeries.get(symbol);
            
            Platform.runLater(() -> {
                // Update chart
                series.getData().add(new XYChart.Data<>(xSeriesData++, price));
                if (series.getData().size() > 50) {
                    series.getData().remove(0);
                }
                
                // Update table
                stockTable.getItems().stream()
                    .filter(item -> item.getSymbol().equals(symbol))
                    .findFirst()
                    .ifPresent(item -> item.setPrice(price));
            });
            
            log(String.format("Updated %s: $%.2f", symbol, price));
        }
    }

    private void refreshAllStocks() {
        stockTrackers.keySet().forEach(this::updateStock);
    }

    @Override
    public void run() {
        log("Stock Monitor Application is running...");

        // Add initial stocks
        addStock("AAPL");
        addStock("GOOGL");
        addStock("MSFT");

        // Schedule periodic updates
        scheduler.scheduleAtFixedRate(this::refreshAllStocks, 0, 5, TimeUnit.SECONDS);
    }

    public static class StockData {
        private final String symbol;
        private double price;
        private double change;

        public StockData(String symbol, double price, double change) {
            this.symbol = symbol;
            this.price = price;
            this.change = change;
        }

        public String getSymbol() { return symbol; }
        public double getPrice() { return price; }
        public double getChange() { return change; }
        
        public void setPrice(double newPrice) {
            change = newPrice - price;
            price = newPrice;
        }

        public javafx.beans.property.StringProperty symbolProperty() {
            return new javafx.beans.property.SimpleStringProperty(symbol);
        }

        public javafx.beans.property.DoubleProperty priceProperty() {
            return new javafx.beans.property.SimpleDoubleProperty(price);
        }

        public javafx.beans.property.DoubleProperty changeProperty() {
            return new javafx.beans.property.SimpleDoubleProperty(change);
        }
    }
} 