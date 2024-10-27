package com.geotrack.apigeotrack.dto.login;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterRequestDTO(@JsonAlias("name") @Schema(description = "Nome completo do Usuário") String name,
                                 @JsonAlias("email") @Schema(description = "Email do Usuário, deve ser único") String email,
                                 @JsonAlias("password") @Schema(description = "Senha com no mínimo 6 caracteres") String password) {
}
