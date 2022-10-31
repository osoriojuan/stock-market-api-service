package cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.rest.stockinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiTimeSeriesQueryValue {
    TIME_SERIES_DAILY,
    TIME_SERIES_WEEKLY,
    TIME_SERIES_MONTHLY;
}
