package com.stockmonitor.display;

import com.stockmonitor.domain.StockPrice;
import com.stockmonitor.interfaces.IChartDisplay;

import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChartDisplay implements IChartDisplay {
    private final LineChart<String, Number> lineChart;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public ChartDisplay() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Price");
        
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Stock Price Chart");
        lineChart.setCreateSymbols(true);
        lineChart.setAnimated(false);
    }
    
    @Override
    public void displayChart() {
        System.out.println("Displaying chart...");
    }
    
    public void displayStockData(List<StockPrice> stockPrices) {
        lineChart.getData().clear();
        
        if (stockPrices.isEmpty()) {
            System.out.println("No data to display");
            return;
        }
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(stockPrices.get(0).getSymbol());
        
        for (StockPrice price : stockPrices) {
            series.getData().add(
                new XYChart.Data<>(price.getDate().format(DATE_FORMATTER), price.getPrice())
            );
        }
        
        lineChart.getData().add(series);
    }
    
    public void compareStocks(List<StockPrice> stockPrices1, List<StockPrice> stockPrices2) {
        lineChart.getData().clear();
        
        if (stockPrices1.isEmpty() || stockPrices2.isEmpty()) {
            System.out.println("Insufficient data for comparison");
            return;
        }
        
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(stockPrices1.get(0).getSymbol());
        
        for (StockPrice price : stockPrices1) {
            series1.getData().add(
                new XYChart.Data<>(price.getDate().format(DATE_FORMATTER), price.getPrice())
            );
        }
        
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName(stockPrices2.get(0).getSymbol());
        
        for (StockPrice price : stockPrices2) {
            series2.getData().add(
                new XYChart.Data<>(price.getDate().format(DATE_FORMATTER), price.getPrice())
            );
        }
        
        lineChart.getData().addAll(series1, series2);
    }
    
    public Node getChartNode() {
        return lineChart;
    }
} 