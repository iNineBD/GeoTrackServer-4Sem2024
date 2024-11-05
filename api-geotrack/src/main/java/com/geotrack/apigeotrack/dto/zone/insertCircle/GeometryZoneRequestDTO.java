package com.geotrack.apigeotrack.dto.zone.insertCircle;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.service.utils.GeometryForms;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "GeometryZoneRequestDTO", description = "DTO para requisição de zona geométrica")
@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryZoneRequestDTO(
        @JsonAlias("name")
        @Schema(description = "Nome da zona",
                example = "Zona Central")
        String name,

        @JsonAlias("type")
        @Schema(description = "Tipo da zona, representado por uma forma geométrica",
                example = "CIRCLE")
        GeometryForms type,

        @JsonAlias("center")
        @Schema(description = "Coordenadas centrais da zona, incluindo latitude e longitude")
        CenterCoordinatesDTO center,

        @JsonAlias("radius")
        @Schema(description = "Raio da zona em metros",
                example = "500.0")
        BigDecimal radius)
{
}
