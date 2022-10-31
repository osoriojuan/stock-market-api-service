package cl.juan.stockmarketapiservice.domain.stockmarket.port;

import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockSymbolInfoRequest;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockValuesByTimeSeries;

public interface StockInfoPort {

    StockValuesByTimeSeries getStockValues(StockSymbolInfoRequest stockSymbolInfoRequest);
}
