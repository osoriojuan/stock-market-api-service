package cl.juan.stockmarketapiservice.domain.stockmarket.model;

import lombok.Getter;

@Getter
public final class StockSymbolInfoRequest {
    private final String stockSymbol;
    private final TimeSeries timeSeries;

    private StockSymbolInfoRequest(String stockSymbol, TimeSeries timeSeries) {
        this.stockSymbol = stockSymbol;
        this.timeSeries = timeSeries;
    }

    public static StockSymbolInfoRequest ofSymbolAndTimeSeries(String stockSymbol, TimeSeries timeSeries) {
        return new StockSymbolInfoRequest(stockSymbol, timeSeries);
    }

    public String toString() {
        return "StockSymbolByTimePeriod(stockSymbol=" + this.getStockSymbol() + ", timeSeries=" + this.getTimeSeries() + ")";
    }
}
