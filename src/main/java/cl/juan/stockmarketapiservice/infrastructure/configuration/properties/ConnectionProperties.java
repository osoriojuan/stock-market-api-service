package cl.juan.stockmarketapiservice.infrastructure.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "stock-market-service.connection")
public class ConnectionProperties {
    private final int connectTimeout;
    private final int readTimeout;
}
