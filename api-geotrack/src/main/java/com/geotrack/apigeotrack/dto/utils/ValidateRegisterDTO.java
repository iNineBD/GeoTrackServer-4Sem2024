package com.geotrack.apigeotrack.dto.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ValidateRegisterDTO", description = "DTO para validação de registro de usuário")
@JsonIgnoreProperties(ignoreUnknown = true)
public record ValidateRegisterDTO(@Schema(description = "Nome do usuário",
        example = "João Silva")
                                  String name,

                                  @Schema(description = "Email do usuário",
                                          example = "joao.silva@example.com")
                                  String email,

                                  @Schema(description = "Senha do usuário",
                                          example = "senhaSegura123")
                                  String password) {
}
