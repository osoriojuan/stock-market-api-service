package cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private int status;
    private String error;
}
