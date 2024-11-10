package com.geotrack.apigeotrack.dto.stopointsessions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "StopPointSessionRequestDTO", description = "DTO para requisição de sessão de ponto de parada")
@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointSessionRequestDTO(@JsonAlias("idDevice")
                                         @Schema(description = "Identificador do dispositivo",
                                                 example = "1")
                                         Long deviceId,

                                         @JsonAlias("startDate")
                                         @Schema(description = "Data de início do período de análise",
                                                 example = "2024-01-01")
                                         LocalDate startDate,

                                         @JsonAlias("endDate")
                                         @Schema(description = "Data de término do período de análise",
                                                 example = "2024-01-31")
                                         LocalDate endDate,

                                         @JsonAlias("centerCoordinates")
                                         @Schema(description = "Coordenadas do centro do ponto de parada")
                                         CenterCoordinatesDTO coordinates,

                                         @JsonAlias("radius")
                                         @Schema(description = "Raio da área de busca em metros",
                                                 example = "1000")
                                         double radius) {
}
