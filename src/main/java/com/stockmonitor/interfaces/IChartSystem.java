package com.stockmonitor.interfaces;

/**
 * Interface for generating and managing price charts as shown in the diagram
 */
public interface IChartSystem {
    /**
     * Enumeration of chart time periods
     */
    enum Period {
        DAY,
        WEEK,
        MONTH,
        QUARTER,
        YEAR,
        CUSTOM
    }
    
    /**
     * Enumeration of technical indicators
     */
    enum IndicatorType {
        MOVING_AVERAGE,
        BOLLINGER_BANDS,
        RSI,
        MACD,
        STOCHASTIC
    }
    
    /**
     * Enumeration of export formats
     */
    enum ExportFormat {
        PNG,
        JPG,
        PDF,
        SVG
    }
    
    /**
     * Chart class to represent a price chart
     */
    class Chart {
        private String symbol;
        private Period period;
        
        public Chart(String symbol, Period period) {
            this.symbol = symbol;
            this.period = period;
        }
        
        public String getSymbol() { return symbol; }
        public Period getPeriod() { return period; }
    }
    
    /**
     * Generate a price chart for a stock
     * @param symbol The stock symbol
     * @param period The time period for the chart
     * @return A Chart object
     */
    Chart generatePriceChart(String symbol, Period period);
    
    /**
     * Add a technical indicator to a chart
     * @param chart The chart to add the indicator to
     * @param indicator The type of indicator to add
     */
    void addIndicator(Chart chart, IndicatorType indicator);
    
    /**
     * Update the display of a chart
     * @param chart The chart to update
     */
    void updateDisplay(Chart chart);
    
    /**
     * Export a chart to a file
     * @param chart The chart to export
     * @param format The format to export in
     * @return The exported chart as a byte array
     */
    byte[] exportChart(Chart chart, ExportFormat format);
} 