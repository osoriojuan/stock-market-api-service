package cl.juan.stockmarketapiservice.domain.stockmarket.usecase;

import cl.juan.stockmarketapiservice.domain.stockmarket.GetStockInfoUseCase;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockMarketInfo;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockSymbolInfoRequest;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockValue;
import cl.juan.stockmarketapiservice.domain.stockmarket.port.StockInfoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GetStockInfo implements GetStockInfoUseCase {

    private final StockInfoPort stockInfoPort;

    @Override
    public StockMarketInfo handle(StockSymbolInfoRequest stockSymbolAndTimeSeries) {
        log.info("Getting stock market info based on symbol [{}] and timeSeries [{}]",
                stockSymbolAndTimeSeries.getStockSymbol(),
                stockSymbolAndTimeSeries.getTimeSeries());

        var stockValues = stockInfoPort.getStockValues(stockSymbolAndTimeSeries);

        var lowerStockValueByPrice = stockValues.getLowerStockValuePrice().orElse(StockValue.empty());
        var highStockValueByPrice = stockValues.getLowerStockValuePrice().orElse(StockValue.empty());
        var latestOpenStockValue = stockValues.getLatestStockValue();
        var variation = stockValues.getPercentualVariationOfClosingPriceBetweenLastTwoStockValues();

        log.info("StockValues obtained successfully");

        return StockMarketInfo.builder()
                .higherPrice(highStockValueByPrice.getHigh())
                .lowerPrice(lowerStockValueByPrice.getLow())
                .openPrice(latestOpenStockValue.getOpen())
                .variationPercentageTwoLastClosingPrices(variation)
                .build();
    }
}
