package com.geotrack.apigeotrack.dto.zone.updateCircle;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.service.utils.GeometryForms;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "UpdateGeometryZonesDTO", description = "DTO para atualização de zona geométrica")
@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateGeometryZonesDTO(
        @JsonAlias("id")
        @Schema(description = "Identificador único da zona",
                example = "1")
        Integer id,

        @JsonAlias("name")
        @Schema(description = "Nome da zona",
                example = "Zona Central")
        String name,

        @JsonAlias("type")
        @Schema(description = "Tipo da zona representado por uma forma geométrica",
                example = "CIRCLE") // Presume-se que o exemplo é um valor válido do enum GeometryForms
        GeometryForms type,

        @JsonAlias("center")
        @Schema(description = "Coordenadas do centro da zona, incluindo latitude e longitude")
        CenterCoordinatesDTO center,

        @JsonAlias("radius")
        @Schema(description = "Raio da zona em metros",
                example = "500.0")
        BigDecimal radius) {
}
