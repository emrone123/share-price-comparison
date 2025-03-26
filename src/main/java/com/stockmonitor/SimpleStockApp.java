package com.stockmonitor;

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

import java.time.LocalDate;
import java.util.Random;

public class SimpleStockApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI Components
        Label titleLabel = new Label("Stock Monitoring Application");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        TextField stockField1 = new TextField();
        stockField1.setPromptText("Enter first stock symbol (e.g., AAPL)");
        
        TextField stockField2 = new TextField();
        stockField2.setPromptText("Enter second stock symbol (e.g., MSFT)");
        
        DatePicker startDate = new DatePicker(LocalDate.now().minusMonths(1));
        DatePicker endDate = new DatePicker(LocalDate.now());
        
        Button viewButton = new Button("View Stock");
        Button compareButton = new Button("Compare Stocks");
        
        // Chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Price");
        
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Stock Price Chart");
        
        // Set up actions
        viewButton.setOnAction(e -> {
            String symbol = stockField1.getText().toUpperCase();
            if (symbol.isEmpty()) {
                showAlert("Please enter a stock symbol");
                return;
            }
            
            // Clear chart and add new data
            lineChart.getData().clear();
            XYChart.Series<String, Number> series = generateSampleData(symbol, 30);
            lineChart.getData().add(series);
        });
        
        compareButton.setOnAction(e -> {
            String symbol1 = stockField1.getText().toUpperCase();
            String symbol2 = stockField2.getText().toUpperCase();
            
            if (symbol1.isEmpty() || symbol2.isEmpty()) {
                showAlert("Please enter both stock symbols");
                return;
            }
            
            // Clear chart and add comparison data
            lineChart.getData().clear();
            XYChart.Series<String, Number> series1 = generateSampleData(symbol1, 30);
            XYChart.Series<String, Number> series2 = generateSampleData(symbol2, 30);
            lineChart.getData().addAll(series1, series2);
        });
        
        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        
        grid.add(new Label("Stock Symbol 1:"), 0, 0);
        grid.add(stockField1, 1, 0);
        grid.add(new Label("Stock Symbol 2:"), 0, 1);
        grid.add(stockField2, 1, 1);
        grid.add(new Label("Start Date:"), 0, 2);
        grid.add(startDate, 1, 2);
        grid.add(new Label("End Date:"), 0, 3);
        grid.add(endDate, 1, 3);
        grid.add(viewButton, 0, 4);
        grid.add(compareButton, 1, 4);
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(titleLabel, grid, lineChart);
        
        // Create scene
        Scene scene = new Scene(root, 800, 600);
        
        // Set the stage
        primaryStage.setTitle("Stock Monitoring Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private XYChart.Series<String, Number> generateSampleData(String symbol, int days) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(symbol);
        
        Random random = new Random();
        double lastPrice = 100 + random.nextDouble() * 50; // Starting price between 100-150
        
        LocalDate today = LocalDate.now();
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            // Add some randomness to prices but maintain a trend
            lastPrice = lastPrice * (1 + (random.nextDouble() - 0.5) * 0.05);
            series.getData().add(new XYChart.Data<>(date.toString(), lastPrice));
        }
        
        return series;
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 