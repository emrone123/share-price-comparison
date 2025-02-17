package adapters;

import core.SharePrice;
import java.time.LocalDate;
import java.util.List;

/**
 * The DataRetriever interface provides a method to fetch daily stock prices
 * for a specified symbol within a given date range.
 */
public interface DataRetriever {
    /**
     * Retrieves a list of SharePrice objects representing the daily prices
     * of the specified stock symbol between the given start and end dates.
     *
     * @param symbol    the stock symbol (e.g., "AAPL" for Apple Inc.)
     * @param startDate the start date of the desired date range
     * @param endDate   the end date of the desired date range
     * @return a list of SharePrice objects containing the daily stock prices
     */
    List<SharePrice> getDailyPrices(String symbol, LocalDate startDate, LocalDate endDate);
}
