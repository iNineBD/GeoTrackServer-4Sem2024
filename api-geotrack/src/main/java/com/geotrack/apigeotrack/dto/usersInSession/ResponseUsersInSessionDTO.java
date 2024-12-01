package com.geotrack.apigeotrack.dto.usersInSession;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseUsersInSessionDTO(
        @JsonAlias("usuarios")
        @Schema(description = "Lista de usuários dentro da sessão")
        List<String> users) {
}
