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
    
    /**
     * Returns a string representation of the SharePrice object.
     * 
     * @return A string displaying the stock data for the specific date
     */
    @Override
    public String toString() {
        return String.format("Date: %s | Open: %.2f | High: %.2f | Low: %.2f | Close: %.2f", 
                             date, open, high, low, close);
    }

    /**
     * Compares this SharePrice object to another object for equality.
     * 
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SharePrice that = (SharePrice) obj;
        return Double.compare(that.open, open) == 0 &&
               Double.compare(that.high, high) == 0 &&
               Double.compare(that.low, low) == 0 &&
               Double.compare(that.close, close) == 0 &&
               date.equals(that.date);
    }

    /**
     * Returns a hash code value for the SharePrice object.
     * 
     * @return A hash code based on the object's fields
     */
    @Override
    public int hashCode() {
        return Objects.hash(date, open, high, low, close);
    }
}
