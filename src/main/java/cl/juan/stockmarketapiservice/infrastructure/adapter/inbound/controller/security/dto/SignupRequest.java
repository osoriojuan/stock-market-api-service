package cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

    @Email(message = "The email format is wrong")
    @NotBlank(message = "Email field is required")
    @Size(max = 50, min = 6, message = "The number of email characters must be between 6 to 50 characters")
    private String email;

    @NotBlank(message = "Password field is required")
    @Size(max = 20, min = 6, message = "The number of password characters must be between 6 to 20 characters")
    private String password;

    @NotBlank(message = "Name field is required")
    @Size(max = 30, message = "The number of name characters be at most 30 characters")
    private String name;

    @NotBlank(message = "Name field is required")
    @Size(max = 30, message = "The number of lastName characters be at most 30 characters")
    private String lastName;
}
