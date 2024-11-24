package com.geotrack.apigeotrack.dto.usersInSession;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "UsersInSessionDTO", description = "DTO para requisição de usuários em sessão")
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestUsersInSessionDTO(
        @JsonAlias("idSession") @Schema(description = "Identificador da sessão", example = "1")
        Integer idSession,
        @JsonAlias("startDate") @Schema(description = "Data de início do período de análise", example = "2024-01-01")
        LocalDate dataInicio,
        @JsonAlias("endDate") @Schema(description = "Data de término do período de análise", example = "2024-01-31")
        LocalDate dataFim) {
}
