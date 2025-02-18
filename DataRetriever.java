package adapters;

import core.SharePrice;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for retrieving stock price data.
 */
public interface DataRetriever {
    /**
     * Fetches daily stock prices for a given symbol and date range.
     *
     * @param symbol    Stock symbol ("AAPL" for Apple Inc.)
     * @param startDate Start date of the range
     * @param endDate   End date of the range
     * @return List of SharePrice objects representing daily prices
     */
    List<SharePrice> getDailyPrices(String symbol, LocalDate startDate, LocalDate endDate);
}
