package cl.juan.stockmarketapiservice.infrastructure.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "stock-market-service.security")
public class SecurityProperties {
    private final String jwtSecret;
    private final int jwtTokenExpirationInMillis;
}