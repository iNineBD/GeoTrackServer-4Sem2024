package com.geotrack.apigeotrack.dto.utils;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.service.utils.GeometryForms;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "ValidateZonesDTO", description = "DTO para validação de zonas")
@JsonIgnoreProperties(ignoreUnknown = true)
public record ValidateZonesDTO(
        @JsonAlias("name")
        @Schema(description = "Nome da zona",
                example = "Zona de Teste")
        String name,

        @JsonAlias("type")
        @Schema(description = "Tipo da zona representado por uma forma geométrica",
                example = "CIRCLE")
        GeometryForms type,

        @JsonAlias("center")
        @Schema(description = "Coordenadas do centro da zona")
        CenterCoordinatesDTO center,

        @JsonAlias("radius")
        @Schema(description = "Raio da zona em metros",
                example = "1000.0")
        BigDecimal radius) {
}
