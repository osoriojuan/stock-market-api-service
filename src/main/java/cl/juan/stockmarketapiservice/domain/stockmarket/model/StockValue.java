package cl.juan.stockmarketapiservice.domain.stockmarket.model;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public final class StockValue {
    private final LocalDate date;
    private final float open;
    private final float high;
    private final float low;
    private final float close;

    public static StockValue create(LocalDate date, float open, float high, float low, float close) {
        return new StockValue(date, open, high, low, close);
    }

    public static StockValue empty() {
        return new StockValue(null, 0, 0, 0, 0);
    }

    private StockValue(LocalDate date, float open, float high, float low, float close) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockValue that)) return false;
        return getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate());
    }

    public String toString() {
        return "StockValue(date=" + this.getDate() + ", open=" + this.getOpen() + ", high=" + this.getHigh() + ", low=" + this.getLow() + ", close=" + this.getClose() + ")";
    }
}
