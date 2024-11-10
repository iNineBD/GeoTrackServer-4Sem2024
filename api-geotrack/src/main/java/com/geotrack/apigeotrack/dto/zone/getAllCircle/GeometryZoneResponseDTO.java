package com.geotrack.apigeotrack.dto.zone.getAllCircle;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.service.utils.GeometryForms;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "GeometryZoneResponseDTO", description = "DTO para resposta de zona geométrica")
@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryZoneResponseDTO(@JsonAlias("id")
                                      @Schema(description = "Identificador único da zona geométrica",
                                              example = "1")
                                      Integer id,

                                      @JsonAlias("name")
                                      @Schema(description = "Nome da zona geométrica",
                                              example = "Zona de Teste")
                                      String name,

                                      @JsonAlias("type")
                                      @Schema(description = "Tipo da zona geométrica representado por uma forma",
                                              example = "CIRCLE")
                                      GeometryForms type,

                                      @JsonAlias("center")
                                      @Schema(description = "Coordenadas do centro da zona")
                                      CenterCoordinatesDTO center,

                                      @JsonAlias("radius")
                                      @Schema(description = "Raio da zona geométrica em metros",
                                              example = "1000.0")
                                      BigDecimal radius) {
}
