package com.geotrack.apigeotrack.dto.login;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AuthenticationRequestDTO", description = "DTO para autenticação de usuário")
@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthenticationRequestDTO(@JsonAlias("email") @Schema(description = "Email do Usuário, deve ser único") String email,
                                       @JsonAlias("password") @Schema(description = "Senha com no mínimo 6 caracteres") String password) {
}
