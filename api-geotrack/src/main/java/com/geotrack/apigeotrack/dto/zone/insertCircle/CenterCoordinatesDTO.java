package com.geotrack.apigeotrack.dto.zone.insertCircle;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "CenterCoordinatesDTO", description = "DTO para coordenadas do centro da zona")
@JsonIgnoreProperties(ignoreUnknown = true)
public record CenterCoordinatesDTO(
        @JsonAlias("longitude")
        @Schema(description = "Longitude da localização",
                example = "-46.633308")
        BigDecimal longitude,

        @JsonAlias("latitude")
        @Schema(description = "Latitude da localização",
                example = "-23.550520")
        BigDecimal latitude) {
}
