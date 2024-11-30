package com.geotrack.apigeotrack.dto.usersInSession;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "UsersInSessionDTO", description = "DTO para requisição de usuários em sessão")
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestUsersInSessionDTO(
        @JsonAlias("idSession") @Schema(description = "Identificador da sessão", example = "1")
        Integer idSession){
}
