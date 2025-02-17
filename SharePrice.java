package core;

import java.time.LocalDate;

/**
 * The SharePrice class represents the stock price information for a specific date.
 * It includes the date and the stock's opening, highest, lowest, and closing prices.
 */
public class SharePrice {
    // The date of the stock prices
    private LocalDate date;

    // The opening price of the stock on the given date
    private double open;

    // The highest price of the stock on the given date
    private double high;

    // The lowest price of the stock on the given date
    private double low;

    // The closing price of the stock on the given date
    private double close;

    /**
     * Constructor to initialize all fields of the SharePrice class.
     *
     * @param date  The date of the stock prices
     * @param open  The opening price on that date
     * @param high  The highest price on that date
     * @param low   The lowest price on that date
     * @param close The closing price on that date
     */
    public SharePrice(LocalDate date, double open, double high, double low, double close) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    // Getter method for the date
    public LocalDate getDate() {
        return date;
    }

    // Getter method for the opening price
    public double getOpen() {
        return open;
    }

    // Getter method for the highest price
    public double getHigh() {
        return high;
    }

    // Getter method for the lowest price
    public double getLow() {
        return low;
    }

    // Getter method for the closing price
    public double getClose() {
        return close;
    }
}
