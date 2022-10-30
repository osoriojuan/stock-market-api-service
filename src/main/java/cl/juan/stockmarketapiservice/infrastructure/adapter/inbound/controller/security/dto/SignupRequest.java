package cl.juan.stockmarketapiservice.infrastructure.adapter.inbound.controller.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "Email is required")
    @Size(max = 50, message = "The email cannot exceed 50 characters")
    @Email(message = "Wrong email format")
    private String email;

    @NotBlank
    @Size(max = 20, message = "The password cannot exceed 20 characters")
    @Size(min = 6, message = "The password cannot be less than 6 characters")
    private String password;

    @NotBlank(message = "Name is required")
    @Size(max = 20, message = "The name cannot exceed 20 characters")
    private String name;

    @NotBlank(message = "lastName is required")
    @Size(max = 20, message = "The lastName cannot exceed 20 characters")
    private String lastName;
}
