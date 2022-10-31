package cl.juan.stockmarketapiservice.infrastructure.configuration;

import cl.juan.stockmarketapiservice.infrastructure.configuration.properties.ConnectionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestConfigClient {

    private final ConnectionProperties connectionProperties;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionProperties.getConnectTimeout());
        requestFactory.setReadTimeout(connectionProperties.getReadTimeout());
        return new RestTemplate(requestFactory);
    }

}
