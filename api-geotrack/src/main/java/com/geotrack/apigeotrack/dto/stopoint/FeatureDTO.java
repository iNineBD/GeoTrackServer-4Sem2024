package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FeatureDTO", description = "DTO para Feature")
@JsonIgnoreProperties(ignoreUnknown = true)
public record FeatureDTO(@JsonAlias("type")
                         @Schema(description = "Tipo da geometria", example = "Polygon")
                         String type,

                         @JsonAlias("properties")
                         @Schema(description = "Propriedades associadas à geometria")
                         PropertiesDTO properties,

                         @JsonAlias("zone")
                         @Schema(description = "Geometria que define a zona", example = "Polygon com coordenadas")
                         GeometryDTO geometry) {
}
