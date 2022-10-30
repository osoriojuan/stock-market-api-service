package cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String email;
    private List<String> roles;
}
