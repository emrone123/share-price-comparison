package core;

import java.time.LocalDate;

/**
 * Represents the stock price information for a specific date.
 */
public class SharePrice {
    private LocalDate date;    // Date of the stock price
    private double open;       // Opening price
    private double high;       // Highest price
    private double low;        // Lowest price
    private double close;      // Closing price

    /**
     * Constructs a SharePrice instance with specified values.
     *
     * @param date  Date of the stock price
     * @param open  Opening price
     * @param high  Highest price
     * @param low   Lowest price
     * @param close Closing price
     */
    public SharePrice(LocalDate date, double open, double high, double low, double close) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    // Getter methods for each attribute
    public LocalDate getDate() { return date; }
    public double getOpen() { return open; }
    public double getHigh() { return high; }
    public double getLow() { return low; }
    public double getClose() { return close; }
}

