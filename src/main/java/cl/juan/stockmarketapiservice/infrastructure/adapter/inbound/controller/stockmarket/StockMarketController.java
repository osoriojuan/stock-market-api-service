package cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.stockmarket;

import cl.juan.stockmarketapiservice.domain.stockmarket.GetStockInfoUseCase;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockSymbolInfoRequest;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.TimeSeries;
import cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.stockmarket.dto.StockMarketInfoResponse;
import cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.stockmarket.dto.TimeSeriesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/stock-market")
@RequiredArgsConstructor
@Validated
public class StockMarketController {

    private final GetStockInfoUseCase getStockInfoUseCase;

    @GetMapping("/stock-info")
    public ResponseEntity<StockMarketInfoResponse> getStockInfo(@Valid
                                                                @NotBlank(message = "stockSymbol parameter is required")
                                                                @RequestParam(name = "stock-symbol") String stockSymbol,
                                                                @Valid
                                                                @NotNull(message = "timeSeries parameter is required")
                                                                @RequestParam(name = "time-series") TimeSeriesRequest timeSeries) {

        var request = StockSymbolInfoRequest.ofSymbolAndTimeSeries(stockSymbol, TimeSeries.valueOf(timeSeries.name()));
        var result = getStockInfoUseCase.handle(request);

        return ResponseEntity.ok(new StockMarketInfoResponse(result.getOpenPrice(),
                result.getHigherPrice(),
                result.getLowerPrice(),
                result.getVariationPercentageTwoLastClosingPrices()));
    }
}
