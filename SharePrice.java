package core;

import java.time.LocalDate; // The {@code SharePrice} class encapsulates the stock price information for a specific date.
public class SharePrice {
    private LocalDate date;
    private double open;
    private double high;
    private double low;
    private double close;

    public SharePrice(LocalDate date, double open, double high, double low, double close) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    // Getters (omitted for brevity)
}
