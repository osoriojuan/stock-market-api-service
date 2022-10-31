package cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.rest.stockinfo.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ApiStockValuesResponse {

    @JsonAnySetter
    private Map<String, ValuesResponse> metadataAndTimeSeries = new HashMap<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class ValuesResponse {

        @JsonAnySetter
        private Map<String, Object> datesAndStockValues = new HashMap<>();

    }

}
