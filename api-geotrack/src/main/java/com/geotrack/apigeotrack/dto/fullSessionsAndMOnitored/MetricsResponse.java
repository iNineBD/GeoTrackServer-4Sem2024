package com.geotrack.apigeotrack.dto.fullSessionsAndMOnitored;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MetricsResponse", description = "Resposta com as métricas do sistema")
@JsonIgnoreProperties(ignoreUnknown = true)
public record MetricsResponse(@Schema(description = "Quantidade de usuários monitorados", example = "100")
                              @JsonAlias("monitored")
                              int qtdMonitored,

                              @Schema(description = "Quantidade de sessões ativas", example = "50")
                              @JsonAlias("sessions")
                              int qtdSessions,

                              @Schema(description = "Quantidade de administradores", example = "5")
                              @JsonAlias("admins")
                              int qtdAdmins) {
}
