package adapters;

import core.SharePrice;

import java.time.LocalDate;
import java.util.List;

public interface DataRetriever {
    List<SharePrice> getDailyPrices(String symbol, LocalDate startDate, LocalDate endDate);
}