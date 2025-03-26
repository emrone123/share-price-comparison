package com.stockmonitor;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.frameworks.service.YahooFinanceService;
import com.stockmonitor.interfaces.service.IStockDataService;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StockComparisonApp extends Application {
    private final IStockDataService stockService = new YahooFinanceService();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void start(Stage primaryStage) {
        // Create simple UI components
        Label titleLabel = new Label("Share Price Comparison");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField stock1Field = new TextField("AAPL");
        TextField stock2Field = new TextField("MSFT");
        
        // Add date pickers for selecting date range
        Label startDateLabel = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker(LocalDate.now().minusMonths(1));
        
        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker(LocalDate.now());
        
        Button compareButton = new Button("Compare Stocks");
        Button refreshButton = new Button("Refresh Data");

        // Set up the chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Price ($)");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Stock Price Comparison");

        // Action for compare button
        compareButton.setOnAction(e -> {
            String stock1 = stock1Field.getText().toUpperCase();
            String stock2 = stock2Field.getText().toUpperCase();
            
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            
            if (startDate == null || endDate == null) {
                startDate = LocalDate.now().minusMonths(1);
                endDate = LocalDate.now();
            }
            
            // Clear previous data
            lineChart.getData().clear();
            
            // Fetch and display real data for both stocks
            List<StockPrice> stockData1 = stockService.fetchStockData(stock1, startDate, endDate);
            List<StockPrice> stockData2 = stockService.fetchStockData(stock2, startDate, endDate);
            
            XYChart.Series<String, Number> series1 = createSeriesFromData(stock1, stockData1);
            XYChart.Series<String, Number> series2 = createSeriesFromData(stock2, stockData2);
            
            lineChart.getData().addAll(series1, series2);
        });

        // Refresh button just regenerates the data
        refreshButton.setOnAction(e -> {
            String stock1 = stock1Field.getText().toUpperCase();
            String stock2 = stock2Field.getText().toUpperCase();
            
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            
            if (startDate == null || endDate == null) {
                startDate = LocalDate.now().minusMonths(1);
                endDate = LocalDate.now();
            }
            
            lineChart.getData().clear();
            
            // Fetch and display real data for both stocks
            List<StockPrice> stockData1 = stockService.fetchStockData(stock1, startDate, endDate);
            List<StockPrice> stockData2 = stockService.fetchStockData(stock2, startDate, endDate);
            
            XYChart.Series<String, Number> series1 = createSeriesFromData(stock1, stockData1);
            XYChart.Series<String, Number> series2 = createSeriesFromData(stock2, stockData2);
            
            lineChart.getData().addAll(series1, series2);
        });

        // Create layout
        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(10));
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        
        inputGrid.add(new Label("Stock 1:"), 0, 0);
        inputGrid.add(stock1Field, 1, 0);
        inputGrid.add(new Label("Stock 2:"), 0, 1);
        inputGrid.add(stock2Field, 1, 1);
        inputGrid.add(startDateLabel, 0, 2);
        inputGrid.add(startDatePicker, 1, 2);
        inputGrid.add(endDateLabel, 0, 3);
        inputGrid.add(endDatePicker, 1, 3);
        inputGrid.add(compareButton, 0, 4);
        inputGrid.add(refreshButton, 1, 4);

        // Main layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, inputGrid, lineChart);

        // Show initial comparison
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();
        List<StockPrice> stockData1 = stockService.fetchStockData("AAPL", startDate, endDate);
        List<StockPrice> stockData2 = stockService.fetchStockData("MSFT", startDate, endDate);
        
        XYChart.Series<String, Number> series1 = createSeriesFromData("AAPL", stockData1);
        XYChart.Series<String, Number> series2 = createSeriesFromData("MSFT", stockData2);
        
        lineChart.getData().addAll(series1, series2);

        // Create scene and show
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Share Price Comparison");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private XYChart.Series<String, Number> createSeriesFromData(String stockSymbol, List<StockPrice> stockData) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(stockSymbol);
        
        for (StockPrice data : stockData) {
            series.getData().add(
                new XYChart.Data<>(data.getDate().format(DATE_FORMATTER), data.getPrice())
            );
        }
        
        return series;
    }

    public static void main(String[] args) {
        launch(args);
    }
} 