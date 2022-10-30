package cl.juan.stockmarketapiservice.infrastructure.configuration.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        SecurityProperties.class
})
public class ApplicationConfiguration {
}
