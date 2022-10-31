package cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.rest.stockinfo;

import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockSymbolInfoRequest;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockValue;
import cl.juan.stockmarketapiservice.domain.stockmarket.model.StockValuesByTimeSeries;
import cl.juan.stockmarketapiservice.domain.stockmarket.port.StockInfoPort;
import cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.rest.stockinfo.dto.ApiStockValues;
import cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.rest.stockinfo.dto.ApiStockValuesResponse;
import cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.rest.stockinfo.dto.ApiTimeSeriesQueryValue;
import cl.juan.stockmarketapiservice.infrastructure.configuration.properties.StockInfoApiProperties;
import cl.juan.stockmarketapiservice.infrastructure.exception.ApiBadResponseException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockInfoClient implements StockInfoPort {

    private final RestTemplate restTemplate;
    private final StockInfoApiProperties stockInfoApiProperties;

    private final static String GENERIC_ERROR_MESSAGE = "An error has occurred while processing your request. Please review the data entered or try again later.";

    @Override
    public StockValuesByTimeSeries getStockValues(StockSymbolInfoRequest stockSymbolInfoRequest) {
        log.info("consuming the api [{}] to get the stock values", stockInfoApiProperties.getUrl());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        var timeSeriesName = stockSymbolInfoRequest.getTimeSeries().name();
        var stockSymbol = stockSymbolInfoRequest.getStockSymbol();
        var urlTemplate = buildUri(timeSeriesName, stockSymbol);

        ResponseEntity<ApiStockValuesResponse> stockValuesApiResponseEntity = executeRequest(httpEntity, urlTemplate);

        ApiStockValuesResponse apiStockValuesResponse = verifyApiResponseAndGetParsedBody(stockValuesApiResponseEntity);
        Set<StockValue> stockValues = collectStockValuesIfArePresent(apiStockValuesResponse);
        return StockValuesByTimeSeries.create(stockValues, stockSymbolInfoRequest.getTimeSeries());
    }

    private ResponseEntity<ApiStockValuesResponse> executeRequest(HttpEntity<?> httpEntity, String urlTemplate) {
        ResponseEntity<ApiStockValuesResponse> response;
        try {
            log.info("Executing request: [{}]",urlTemplate);
            response = restTemplate.exchange(urlTemplate,
                    HttpMethod.GET,
                    httpEntity,
                    ApiStockValuesResponse.class);
        } catch (Exception e) {
            log.error("Request to service [{}] was unsuccessful", stockInfoApiProperties.getUrl());
            throw apiResponseException(e);
        }
        return response;
    }

    private ApiBadResponseException apiResponseException(Exception e) {
        if(e.getMessage().contains("Invalid API call")){
            log.error("The entered stock symbol is currently unavailable. Please try another.");
            return new ApiBadResponseException("The entered stock symbol is currently unavailable. Please try another.");
        }
        return new ApiBadResponseException(GENERIC_ERROR_MESSAGE);
    }

    private ApiStockValuesResponse verifyApiResponseAndGetParsedBody(ResponseEntity<ApiStockValuesResponse> stockValuesApiResponseEntity) {
        if (stockValuesApiResponseEntity.getBody() == null) {
            log.error("The body of the response to the api [{}] is empty ", stockInfoApiProperties.getUrl());
            throw new ApiBadResponseException(GENERIC_ERROR_MESSAGE);
        }
        return stockValuesApiResponseEntity.getBody();
    }

    private Set<StockValue> collectStockValuesIfArePresent(ApiStockValuesResponse apiStockValuesResponse) {
        return apiStockValuesResponse.getMetadataAndTimeSeries()
                .entrySet().stream()
                .filter(stringValuesResponseEntry -> !stringValuesResponseEntry.getKey().contains("Meta Data"))
                .findFirst()
                .map(stringValuesResponseEntry -> getListOfStockValues(stringValuesResponseEntry.getValue()))
                .orElseThrow(() -> new ApiBadResponseException(GENERIC_ERROR_MESSAGE));
    }

    private String buildUri(String timeSeriesName, String stockSymbol) {
        return UriComponentsBuilder.fromHttpUrl(stockInfoApiProperties.getUrl())
                .queryParam("function", ApiTimeSeriesQueryValue.valueOf(timeSeriesName))
                .queryParam("symbol", stockSymbol)
                .queryParam("apikey", stockInfoApiProperties.getApikey())
                .encode().toUriString();
    }

    private Set<StockValue> getListOfStockValues(ApiStockValuesResponse.ValuesResponse values) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Gson gson = new Gson();

        return values.getDatesAndStockValues()
                .entrySet()
                .stream()
                .map(dateAndStockValues -> {
                    var jsonStockValue = gson.toJson(dateAndStockValues.getValue());
                    var apiStockValues = gson.fromJson(jsonStockValue, ApiStockValues.class);
                    var date = LocalDate.parse(dateAndStockValues.getKey(), formatter);
                    return StockValue.create(date, Float.parseFloat(apiStockValues.getOpen()),
                            Float.parseFloat(apiStockValues.getHigh()),
                            Float.parseFloat(apiStockValues.getLow()),
                            Float.parseFloat(apiStockValues.getClose()));
                }).collect(Collectors.toSet());
    }
}
