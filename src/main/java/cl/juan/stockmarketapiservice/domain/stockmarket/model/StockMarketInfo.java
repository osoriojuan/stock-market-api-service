package cl.juan.stockmarketapiservice.domain.stockmarket.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class StockMarketInfo {
    private final float openPrice;
    private final float higherPrice;
    private final float lowerPrice;
    private final float variationPercentageTwoLastClosingPrices;

    public String toString() {
        return "StockMarketInfo(openPrice=" + this.getOpenPrice() + ", higherPrice=" + this.getHigherPrice() + ", lowerPrice=" + this.getLowerPrice() + ", variationPercentageTwoLastClosingPrices=" + this.getVariationPercentageTwoLastClosingPrices() + ")";
    }
}
