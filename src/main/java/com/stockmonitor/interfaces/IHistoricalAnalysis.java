package com.stockmonitor.interfaces;

import com.stockmonitor.domain.StockPrice;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface for analyzing historical stock data as shown in the diagram
 */
public interface IHistoricalAnalysis {
    /**
     * Analysis result class to store analysis outcomes
     */
    class AnalysisResult {
        private String symbol;
        private LocalDate startDate;
        private LocalDate endDate;
        private Map<String, Object> metrics;
        
        public AnalysisResult(String symbol, LocalDate startDate, LocalDate endDate, Map<String, Object> metrics) {
            this.symbol = symbol;
            this.startDate = startDate;
            this.endDate = endDate;
            this.metrics = metrics;
        }
        
        public String getSymbol() { return symbol; }
        public LocalDate getStartDate() { return startDate; }
        public LocalDate getEndDate() { return endDate; }
        public Map<String, Object> getMetrics() { return metrics; }
    }
    
    /**
     * Analyze historical data for a stock
     * @param symbol The stock symbol
     * @param startDate The start date for analysis
     * @param endDate The end date for analysis
     * @return Analysis result
     */
    AnalysisResult analyzeHistory(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * Calculate statistical metrics for a set of stock data
     * @param data List of stock data points
     * @return Map of metric names to values
     */
    Map<String, Object> calculateMetrics(List<StockPrice> data);
    
    /**
     * Generate a trend prediction model for a stock
     * @param symbol The stock symbol
     * @return A prediction model as a map of dates to predicted prices
     */
    Map<LocalDate, Double> generateTrendModel(String symbol);
    
    /**
     * Export analysis results to a report
     * @param result The analysis result to export
     * @return Whether the export was successful
     */
    boolean exportAnalysisReport(AnalysisResult result);
} 