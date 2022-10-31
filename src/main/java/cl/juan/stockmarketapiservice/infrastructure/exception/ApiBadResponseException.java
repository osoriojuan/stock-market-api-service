package cl.juan.stockmarketapiservice.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class ApiBadResponseException extends RuntimeException {

    public ApiBadResponseException(String message) {
        super(message);
    }
}
