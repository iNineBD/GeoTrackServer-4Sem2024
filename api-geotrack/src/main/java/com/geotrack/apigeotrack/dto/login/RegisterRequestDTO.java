package com.geotrack.apigeotrack.dto.login;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterRequestDTO(@JsonAlias("name") String name,
                                 @JsonAlias("email") @Email(message = "Por favor, insira um email válido!") String email,
                                 @JsonAlias("password") String password) {
}
