package com.geotrack.apigeotrack.dto.login;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResponseDTO", description = "Resposta com o token de autenticação")
@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginResponseDTO(@JsonAlias("token")
                               @Schema(description = "Token de autenticação JWT", example =
                                       "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
                               String token) {
}
