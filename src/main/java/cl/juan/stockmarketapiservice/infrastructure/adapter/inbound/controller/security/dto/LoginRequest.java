package cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
