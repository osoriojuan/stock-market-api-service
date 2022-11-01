package cl.juan.stockmarketapiservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@Slf4j
public class StockMarketApiServiceApplication {

    public static void main(String[] args) {
        log.info("Starting v1 app");
        SpringApplication.run(StockMarketApiServiceApplication.class, args);
    }

}
