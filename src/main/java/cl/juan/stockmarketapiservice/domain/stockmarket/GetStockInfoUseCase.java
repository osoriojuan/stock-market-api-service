package cl.juan.stockmarketapiservice.domain.stockmarket;

import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockMarketInfo;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockSymbolInfoRequest;

public interface GetStockInfoUseCase {
    StockMarketInfo handle(StockSymbolInfoRequest stockSymbolAndTimeSeries);
}
