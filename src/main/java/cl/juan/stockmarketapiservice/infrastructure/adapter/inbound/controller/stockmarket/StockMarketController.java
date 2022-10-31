package cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.stockmarket;

import cl.juan.stockmarketapiservice.domain.stockmarket.GetStockInfoUseCase;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockSymbolInfoRequest;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.TimeSeries;
import cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.stockmarket.dto.StockMarketInfoResponse;
import cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.stockmarket.dto.TimeSeriesRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/stock-market")
@RequiredArgsConstructor
public class StockMarketController {

    private final GetStockInfoUseCase getStockInfoUseCase;

    @GetMapping("/stock-info")
    public ResponseEntity<StockMarketInfoResponse> getStockInfo(@Valid
                                                                @NotBlank(message = "stockSymbol parameter is required")
                                                                @RequestParam(name = "stock-symbol") String stockSymbol,
                                                                @Valid
                                                                @NotNull(message = "stockSymbol parameter is required")
                                                                @RequestParam(name = "time-series", required = false) TimeSeriesRequest timeSeries) {

        var request = StockSymbolInfoRequest.ofSymbolAndTimeSeries(stockSymbol, TimeSeries.valueOf(timeSeries.name()));
        var result = getStockInfoUseCase.handle(request);

        return ResponseEntity.ok(new StockMarketInfoResponse(result.getOpenPrice(),
                result.getHigherPrice(),
                result.getLowerPrice(),
                result.getVariationPercentageTwoLastClosingPrices()));
    }
}
