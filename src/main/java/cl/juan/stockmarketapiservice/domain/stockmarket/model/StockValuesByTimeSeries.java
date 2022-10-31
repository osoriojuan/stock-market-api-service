package cl.juan.stockmarketapiservice.domain.stockmarket.model;

import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public final class StockValuesByTimeSeries {

    private final Set<StockValue> stockValues;
    private final TimeSeries timeSeries;

    public static StockValuesByTimeSeries create(Set<StockValue> stockValues, TimeSeries timeSeries) {
        return new StockValuesByTimeSeries(stockValues, timeSeries);
    }

    private StockValuesByTimeSeries(Set<StockValue> stockValues, TimeSeries timeSeries) {
        this.stockValues = stockValues;
        this.timeSeries = timeSeries;
    }

    public String toString() {
        return "StockValuesByTimeSeries(timeSeries=" + this.getTimeSeries() + ", stockValues=" + this.getStockValues() + ")";
    }

    public List<StockValue> getSortedStockValuesByDate() {
        return stockValues.stream().sorted(Comparator.comparing(StockValue::getDate))
                .collect(Collectors.toList());
    }

    public Optional<StockValue> getHigherStockValuePrice() {
        return stockValues.stream().max(Comparator.comparing(StockValue::getHigh));
    }

    public Optional<StockValue> getLowerStockValuePrice() {
        return stockValues.stream().min(Comparator.comparing(StockValue::getLow));
    }

    public StockValue getLatestStockValue() {
        return this.getSortedStockValuesByDate().get(getStockValues().size() - 1);
    }

    public float getVariationOfClosingPriceBetweenLastTwoStockValues() {
        var sortedStockValuesByDate = this.getSortedStockValuesByDate();
        var latest = sortedStockValuesByDate.get(sortedStockValuesByDate.size() - 1);
        var secondToLast = sortedStockValuesByDate.get(sortedStockValuesByDate.size() - 2);

        return (latest.getClose() - secondToLast.getClose()) / secondToLast.getClose() * 100;
    }
}
