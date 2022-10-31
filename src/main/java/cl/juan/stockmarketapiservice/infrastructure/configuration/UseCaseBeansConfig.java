package cl.juan.stockmarketapiservice.infrastructure.configuration;

import cl.juan.stockmarketapiservice.domain.stockmarket.GetStockInfoUseCase;
import cl.juan.stockmarketapiservice.domain.stockmarket.port.StockInfoPort;
import cl.juan.stockmarketapiservice.domain.stockmarket.usecase.GetStockInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeansConfig {

    @Bean
    public GetStockInfoUseCase countryCodesGetterUseCase(StockInfoPort stockInfoPort) {
        return new GetStockInfo(stockInfoPort);
    }

}
